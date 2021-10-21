package ru.home.intructor_bot.cache;


import ru.home.intructor_bot.botapi.botstates.BotState;
import ru.home.intructor_bot.botapi.UserProfileData;


public interface DataCache {
    void setUsersCurrentBotState(int userId, BotState botState);

    BotState getUsersCurrentBotState(int userId);

    UserProfileData getUserProfileData(int userId);

    void saveUserProfileData(int userId, UserProfileData userProfileData);
}
