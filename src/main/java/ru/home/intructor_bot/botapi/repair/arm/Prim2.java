package ru.home.intructor_bot.botapi.repair.arm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.intructor_bot.botapi.botstates.BotState;
import ru.home.intructor_bot.botapi.InputMessageHandler;
import ru.home.intructor_bot.botapi.UserProfileData;
import ru.home.intructor_bot.cache.UserDataCache;
import ru.home.intructor_bot.Service.ReplyMessagesService;


@Slf4j
@Component
public class Prim2 implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    public Prim2(UserDataCache userDataCache,
                 ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }


    @Override
    public BotState getHandlerName() {
        return BotState.PRIM2;
    }


    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();


        UserProfileData profileData = userDataCache.getUserProfileData(userId);


        SendMessage replyToUser = null;


        if (usersAnswer.equals("1")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.prim41");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_HI_TAG);
        }
        if (usersAnswer.equals("2")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.prim42");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_HI_TAG);
        }
        if (usersAnswer.equals("3")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.prim43");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_HI_TAG);
        }
        if (usersAnswer.equals("4")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.error");
            userDataCache.setUsersCurrentBotState(userId, BotState.PRIM2);
        }
        if (usersAnswer.equals("Назад")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.prim");
            userDataCache.setUsersCurrentBotState(userId, BotState.PRIM);
        }

        userDataCache.saveUserProfileData(userId, profileData);

        return replyToUser;
    }


}