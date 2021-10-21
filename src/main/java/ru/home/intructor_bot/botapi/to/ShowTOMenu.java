package ru.home.intructor_bot.botapi.to;


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
public class ShowTOMenu implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    public ShowTOMenu(UserDataCache userDataCache,
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
        return BotState.SHOW_TO_MENU;
    }


    private SendMessage processUsersInput(Message inputMsg) {
        String usersAnswer = inputMsg.getText();
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();


        UserProfileData profileData = userDataCache.getUserProfileData(userId);


        SendMessage replyToUser = null;

        if (usersAnswer.equals("1")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.to2");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_HI_TAG);
        }
        if (usersAnswer.equals("2")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.to3");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_HI_TAG);
        }
        if (usersAnswer.equals("3")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.to4");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_HI_TAG);
        }
        if (usersAnswer.equals("4")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.to5");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_HI_TAG);
        }
        if (usersAnswer.equals("Назад")) {
            replyToUser = messagesService.getReplyMessage(chatId, "reply.hi");
            userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MENU);
        }

        userDataCache.saveUserProfileData(userId, profileData);

        return replyToUser;
    }
}