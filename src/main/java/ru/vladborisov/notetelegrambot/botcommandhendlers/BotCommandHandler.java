package ru.vladborisov.notetelegrambot.botcommandhendlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommandHandler{
    String getCommand();
    SendMessage processCommand(Update update);
}
