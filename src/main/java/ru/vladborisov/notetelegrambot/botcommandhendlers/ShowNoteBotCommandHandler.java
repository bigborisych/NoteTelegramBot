package ru.vladborisov.notetelegrambot.botcommandhendlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vladborisov.notetelegrambot.model.Note;
import ru.vladborisov.notetelegrambot.service.NotesService;

import java.util.List;

@Component
public class ShowNoteBotCommandHandler implements BotCommandHandler {
    private static final String SHOW_NOTE_COMMAND = "/shownote";
    @Autowired
    private NotesService notesService;

    @Override
    public String getCommand() {
        return SHOW_NOTE_COMMAND;
    }

    @Override
    public SendMessage processCommand(Update update) {
        SendMessage message = new SendMessage();
        List<Note> noteList = notesService.getNotes();
        StringBuilder stringBuilder = new StringBuilder();
        Long chatId = update.getMessage().getChatId();
        message.setChatId(String.valueOf(chatId));
        for (Note note : noteList) {
            stringBuilder.append("Заметка номер: ");
            stringBuilder.append(note.getId());
            stringBuilder.append("\n");
            stringBuilder.append(note.getNoteContent());
            stringBuilder.append("\n");
        }
        message.setText(stringBuilder.toString());
        return message;
    }
}
