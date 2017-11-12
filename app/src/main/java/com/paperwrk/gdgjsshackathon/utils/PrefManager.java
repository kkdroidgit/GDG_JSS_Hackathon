package com.paperwrk.gdgjsshackathon.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kartik on 11-11-17.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_BOOK = "Activity_Name";
    private static final String IS_ARTIST = "Activity_Name";


    public PrefManager(Context context) {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setBook(boolean isBook){
        editor.putBoolean(IS_BOOK,isBook);
        editor.commit();
    }
    public boolean isBook(){
        return pref.getBoolean(IS_BOOK,true);
    }

}
