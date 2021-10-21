package ru.home.intructor_bot.botapi.pic;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.intructor_bot.botapi.botstates.BotState;
import ru.home.intructor_bot.cache.UserDataCache;
import ru.home.intructor_bot.Service.ReplyMessagesService;




@Slf4j
@Component
public class SendDickPick implements DickSender {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    public SendDickPick(UserDataCache userDataCache,
                        ReplyMessagesService messagesService) {
        this.userDataCache = userDataCache;
        this.messagesService = messagesService;
    }




    @Override
    public BotState getHandlerName() {
        return BotState.SEND_DICK;
    }

    @Override
    public SendPhoto handlerPhoto(Message message) {
        return processUsersInput(message);
    }

    private SendPhoto processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        SendPhoto replyToUser = null;
        replyToUser = messagesService.getReplyPhoto(chatId);
        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MENU);

        return replyToUser;
    }}