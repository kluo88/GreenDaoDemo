package com.itkluo.greendaodemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.itkluo.greendaodemo.db.BaseCacheManager;
import com.itkluo.greendaodemo.db.UserInfoManager;
import com.itkluo.greendaodemo.db.entity.UserInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private long i = 1;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_result = findViewById(R.id.tv_result);
        MyApp.getInstance().initCacheDB();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserInfoManager.getInstance().closeDB();
    }

    public void clickInsert(View view) {
        UserInfo userinfo = new UserInfo(++i, "乔布斯" + i, String.valueOf(1000 + i));
        userinfo.loginCount = (int) i;
        UserInfoManager.getInstance().addUserInfo(userinfo);
        getAllData();
    }

    public void clickDelete(View view) {
        UserInfoManager.getInstance().deleteAllUserInfo();
        i = 1;
        getAllData();
    }

    public void clickSearch(View view) {
        getAllData();
    }

    private void getAllData() {
        UserInfoManager.getInstance().getUserInfo(new BaseCacheManager.ResultCallback<List<UserInfo>>() {
            @Override
            public void onSuccess(List<UserInfo> userInfos) {
                tv_result.setText(userInfos.toString());
            }
        });
    }


}
