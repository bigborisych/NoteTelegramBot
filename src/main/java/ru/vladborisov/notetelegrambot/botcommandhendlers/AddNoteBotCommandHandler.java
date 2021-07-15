package ru.vladborisov.notetelegrambot.botcommandhendlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vladborisov.notetelegrambot.model.Note;
import ru.vladborisov.notetelegrambot.model.Tag;
import ru.vladborisov.notetelegrambot.service.NotesService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AddNoteBotCommandHandler implements BotCommandHandler {
    private static final String ADD_NOTE_COMMAND = "/note";
    private static final String SUCCESSFUL_NOTE_ADDED = "Заметка добавлена!";
    @Autowired
    private NotesService notesService;

    @Override
    public String getCommand() {
        return ADD_NOTE_COMMAND;
    }

    @Override
    public SendMessage processCommand(Update update) {
        Long chatId = update.getMessage().getChatId();
        String[] note = update.getMessage().getText().replaceFirst(ADD_NOTE_COMMAND, "").trim().split(" ");
        String content = note[1];
        String[] tags = note[0].replaceFirst("#", "").split("#");
        Set<Tag> noteTagSet = Arrays
                .stream(tags)
                .map((tagName) -> {
                    Tag tag = notesService.getTagByName(tagName);
                    if (tag == null) {
                        tag = new Tag(tagName);
                        notesService.addTag(tag);
                    }
                    return tag;
                })
                .collect(Collectors.toSet());
        notesService.addNote(new Note(noteTagSet, content));
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(SUCCESSFUL_NOTE_ADDED);
        return message;
    }
}
