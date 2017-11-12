package com.paperwrk.gdgjsshackathon.utils;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Kartik on 10-7-17.
 */

public class EnableMultiDex extends MultiDexApplication {
    private static EnableMultiDex enableMultiDex;
    public static Context context;

    public EnableMultiDex(){
        enableMultiDex=this;
    }

    public static EnableMultiDex getEnableMultiDexApp() {
        return enableMultiDex;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

    }
}
