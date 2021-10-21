package ru.home.intructor_bot.botapi.himenu;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.intructor_bot.botapi.botstates.BotState;
import ru.home.intructor_bot.botapi.InputMessageHandler;
import ru.home.intructor_bot.cache.UserDataCache;
import ru.home.intructor_bot.Service.ReplyMessagesService;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class HiHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessagesService messagesService;

    public HiHandler(UserDataCache userDataCache,
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
        return BotState.SHOW_HI_TAG;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        int userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();


        SendMessage replyToUser = messagesService.getReplyMessage(chatId, "reply.hi");
        userDataCache.setUsersCurrentBotState(userId, BotState.SHOW_MENU);
        replyToUser.setReplyMarkup(getInlineMessageButtons());

        return replyToUser;
    }

    private InlineKeyboardMarkup getInlineMessageButtons() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        InlineKeyboardButton buttonMainMenu = new InlineKeyboardButton().setText("Открыть клавиатуру");


        buttonMainMenu.setCallbackData(" buttonMainMenu");


        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow2.add(buttonMainMenu);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }

}
