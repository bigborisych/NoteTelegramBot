package ru.vladborisov.notetelegrambot.botmodel;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.vladborisov.notetelegrambot.botcommandhendlers.BotCommandHandler;
import ru.vladborisov.notetelegrambot.model.Note;

import java.util.List;


@Component
public class NoteBot extends TelegramLongPollingBot {
    @Autowired
    private List<BotCommandHandler> handlers;

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    public NoteBot(@Value("${bot.name}") String botName, @Value("${bot.token}") String botToken) {
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            for (BotCommandHandler botCommandHandler : handlers) {
                if (update.getMessage().getText().contains(botCommandHandler.getCommand())) {
                    execute(botCommandHandler.processCommand(update));
                }
            }
        }
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     *
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     *
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}