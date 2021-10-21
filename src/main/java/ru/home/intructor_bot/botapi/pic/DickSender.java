package ru.home.intructor_bot.botapi.pic;

import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.intructor_bot.botapi.botstates.BotState;

public interface DickSender {
    BotState getHandlerName();
    SendPhoto handlerPhoto(Message message);
}
