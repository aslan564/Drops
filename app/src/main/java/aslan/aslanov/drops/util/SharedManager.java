package aslan.aslanov.drops.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedManager {

    private static final String SHARED_PREFERENCE_MANAGER = "aslan.aslanov.drops.util";
    private static final String LOGIN_OR_REGISTRY = "loginOrRegistry";


    public static void setLoginOrRegister(Context context, Boolean status) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_MANAGER, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(LOGIN_OR_REGISTRY, status).apply();
    }

    public static Boolean getLoginStatus(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_MANAGER, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(LOGIN_OR_REGISTRY, false);
    }
}
