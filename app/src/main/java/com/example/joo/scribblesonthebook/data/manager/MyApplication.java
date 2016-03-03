package com.example.joo.scribblesonthebook.data.manager;

import android.app.Application;
import android.content.Context;

/**
 * Created by Joo on 2016-03-03.
 */
public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }
}
