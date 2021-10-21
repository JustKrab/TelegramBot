package ru.home.intructor_bot.botapi.repair.menus;

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
public class TerminalMenu2 implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    public TerminalMenu2(UserDataCache userDataCache,
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
        return BotState.TERMINAL2;
    }


    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();


        UserProfileData profileData = userDataCache.getUserProfileData(userId);


        SendMessage replyToUser = null;


        if (usersAnswer.equals("1")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.screen");
            userDataCache.setUsersCurrentBotState(userId, BotState.SCREEN);

        }
        if (usersAnswer.equals("2")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.reader");
            userDataCache.setUsersCurrentBotState(userId, BotState.READER);
        }
        if (usersAnswer.equals("3")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.kuper");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_HI_TAG);
        }
        if (usersAnswer.equals("4")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.prim08");
            userDataCache.setUsersCurrentBotState(userId, BotState.PRIM08);
        }
        if (usersAnswer.equals("Назад")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.ter");
            userDataCache.setUsersCurrentBotState(userId, BotState.TERMINAL);
        }


        userDataCache.saveUserProfileData(userId, profileData);

        return replyToUser;
    }


}