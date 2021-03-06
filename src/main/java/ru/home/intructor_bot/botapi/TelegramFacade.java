package ru.home.intructor_bot.botapi;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.intructor_bot.Service.MainMenuService;
import ru.home.intructor_bot.Service.ReplyMessagesService;
import ru.home.intructor_bot.botapi.botstates.BotState;
import ru.home.intructor_bot.botapi.botstates.BotStateContext;
import ru.home.intructor_bot.cache.UserDataCache;

/**
 * @author Sazhin Vladislav
 * @thanks Sergei Viacheslaev
 */
@Component
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;
    private MainMenuService mainMenuService;
    private ReplyMessagesService messagesService;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache, MainMenuService mainMenuService, ReplyMessagesService messagesService) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.mainMenuService = mainMenuService;
        this.messagesService = messagesService;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;


        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
//            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
//                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }



        Message message = update.getMessage();
        if (message != null && message.hasText()) {
//            log.info("New message from User:{}, chatId: {},  with text: {}",
//                    message.getFrom().getUserName(), message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        return replyMessage;
    }


    private SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage = null;
        long chatId = message.getChatId();

        switch (inputMsg) {
            case "/start":
                botState = BotState.SHOW_HI_TAG;
                break;
            case "?????????????? ????????":
                botState = BotState.SHOW_HI_TAG;
                break;
            case "??????????????????":
                botState = BotState.SHOW_DOC_MENU;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.documents");
                break;
            case "??????????":
                botState = BotState.SCREEN;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.screen");
                break;
            case "????????????????????":
                botState = BotState.CONTROLLER;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.controller");
                break;
            case "????????????":
                botState = BotState.HOPPER;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.hopper");
                break;
            case "????????08":
                botState = BotState.PRIM08;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.prim08");
                break;
            case "??????????":
                botState = BotState.READER;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.reader");
                break;
            case "?????????????????????? ??????????????":
                botState = BotState.INDUCKT_SENSERS;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.induct");
                break;
            case "??????":
                botState = BotState.IBP;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.ibp");
                break;
            case "??????":
                botState = BotState.IBP;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.ibp");
                break;
            case "????????????????":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.moneta");
                break;
            case "????????????????":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.kuper");
                break;
            case "???????????????????? ??????????????":
                botState = BotState.OPTIC_SENSERS;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.optic");
                break;
            case "??????????????????":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.validator");
                break;
            case "??????????":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.mufta");
                break;
            case "????2":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.to2");
                break;
            case "????3":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.to3");
                break;
            case "????4":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.to4");
                break;
            case "??????????":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.to5");
                break;
            case "??????????????":
                botState = BotState.MONITOR;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.monitor");
                break;
            case "????????":
                botState = BotState.PRIM;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.prim");
                break;
            case "?????????????????? ????????":
                botState = BotState.SYSTEM_BLOCK;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.system");
                break;
            case "89992156598":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.pashalka");
                break;
            case "????????????":
                botState = BotState.SHOW_HI_TAG;
                replyMessage = messagesService.getReplyMessage(chatId, "reply.help");
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState(userId, botState);

        if (replyMessage == null)
            replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }

    private BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {
        final long chatId = buttonQuery.getMessage().getChatId();
        final int userId = buttonQuery.getFrom().getId();
        BotApiMethod<?> callBackAnswer = mainMenuService.getMainMenuMessage(chatId, "?????????????????????? ???????????????????? ?????? ??????????.");


        BotState is = userDataCache.getUsersCurrentBotState(userId);
        userDataCache.setUsersCurrentBotState(userId, is);


        return callBackAnswer;


    }





}
