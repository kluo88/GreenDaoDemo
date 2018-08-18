package com.itkluo.greendaodemo;

import android.app.Application;

import com.itkluo.greendaodemo.db.UserInfoManager;

/**
 * Created by luobingyong on 2018/8/18.
 */
public class MyApp extends Application {
    private static MyApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApp getInstance() {
        return instance;
    }

    public void initCacheDB() {
        UserInfoManager.init(this);
        UserInfoManager.getInstance().openDB();
    }


}
