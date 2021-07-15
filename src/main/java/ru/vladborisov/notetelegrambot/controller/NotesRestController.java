package ru.vladborisov.notetelegrambot.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vladborisov.notetelegrambot.botmodel.NoteBot;
import ru.vladborisov.notetelegrambot.model.Note;
import ru.vladborisov.notetelegrambot.model.Tag;
import ru.vladborisov.notetelegrambot.service.NotesService;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class NotesRestController {

    @Autowired
    private NotesService notesService;
    @Autowired
    private NoteBot noteBot;

    private BotSession botSession;
    private boolean flag = true;

    @GetMapping("/notes")
    public ResponseEntity<List<Note>> getNotes() {
        return new ResponseEntity<>(notesService.getNotes(), HttpStatus.OK);
    }

    @GetMapping("/tags")
    public ResponseEntity<Set<Tag>> getTags() {
        return new ResponseEntity<>(notesService.getTags(), HttpStatus.OK);
    }

    @SneakyThrows
    @GetMapping("/startNoteBot")
    public ResponseEntity<JsonNode> startBot() {
        String jsonString = "{\"status\": \"bot_started\"}";

        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(jsonString);
        ObjectNode actualObj = mapper.readTree(parser);
        if (botSession == null) {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botSession = botsApi.registerBot(noteBot);
        } else {
            actualObj.put("status", "bot_working");
        }
        return new ResponseEntity<>(actualObj, HttpStatus.OK);
    }
    @Async
    @SneakyThrows
    @GetMapping("/stopNoteBot")
    public Future<ResponseEntity<JsonNode>> stopBot() {
        String jsonString = "{\"status\": \"bot_not_stopped\"}";
        ObjectMapper mapper = new ObjectMapper();
        JsonFactory factory = mapper.getFactory();
        JsonParser parser = factory.createParser(jsonString);
        ObjectNode actualObj = mapper.readTree(parser);
        if(botSession.isRunning() && flag){
            flag = false;
            botSession.stop();
            actualObj.put("status", "bot_stopped");
        }
        return AsyncResult.forValue(new ResponseEntity<>(actualObj, HttpStatus.OK));
    }
}
