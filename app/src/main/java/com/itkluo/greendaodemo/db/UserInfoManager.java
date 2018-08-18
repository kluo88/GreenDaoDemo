package com.itkluo.greendaodemo.db;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.itkluo.greendaodemo.db.entity.UserInfo;
import com.itkluo.greendaodemo.greendao.UserInfoDao;
import com.itkluo.greendaodemo.utils.LogUtil;

import java.util.List;


/**
 * 用户个人信息 缓存数据库访问接口
 * Created by luobingyong on 2018/8/17.
 */
public class UserInfoManager extends BaseCacheManager {
    private static final String TAG = "UserInfoManager";
    private static UserInfoManager sInstance;
    private GreenDBManager mDBManager;

    private UserInfoDao mUserInfoDao;

    public static UserInfoManager getInstance() {
        return sInstance;
    }

    public UserInfoManager(Context context) {
        super(context);
    }

    public static void init(Context context) {
        LogUtil.d(TAG, "init");
        sInstance = new UserInfoManager(context);
    }

    @Override
    public void openDB() {
        LogUtil.d(TAG, "openDB");
        if (mDBManager == null) {
            mDBManager = GreenDBManager.getInstance(mContext);
            mWorkThread = new HandlerThread(TAG);
            mWorkThread.start();
            mWorkHandler = new Handler(mWorkThread.getLooper());
            mUserInfoDao = GreenDBManager.getDaoSession(mContext).getUserInfoDao();
        }
    }

    @Override
    public void closeDB() {
        LogUtil.d(TAG, "closeDB");
        if (mDBManager != null) {
            mDBManager.uninit();
            mDBManager = null;
            mUserInfoDao = null;
        }
        if (mWorkThread != null) {
            mWorkThread.quit();
            mWorkThread = null;
            mWorkHandler = null;
        }
    }

    /**
     * 同步接口, 获取收集的名片
     *
     * @param myUserId 自己ID
     * @return
     */
    public UserInfo getUserInfoByID(long myUserId) {
        try {
            if (mUserInfoDao != null) {
                return mUserInfoDao.queryBuilder().where(UserInfoDao.Properties.User_id.eq(myUserId)).build().unique();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 异步接口,获取1个好友信息
     *
     * @param myUserId 自己ID
     * @param callback 获取好友信息回调
     */
    public void getUserInfoByID(final long myUserId, final ResultCallback<UserInfo> callback) {
        mWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                UserInfo userInfo = null;
                try {
                    if (mUserInfoDao != null) {
                        userInfo = mUserInfoDao.queryBuilder().where(UserInfoDao.Properties.User_id.eq(myUserId)).build().unique();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (callback != null) {
                    callback.onCallback(userInfo);
                }
                LogUtil.d(TAG, "getUserInfoFrom DB: " + userInfo);
            }
        });
    }

    public void getUserInfo(final ResultCallback<List<UserInfo>> callback) {
        mWorkHandler.post(new Runnable() {
            @Override
            public void run() {
                List<UserInfo> userInfos = null;
                try {
                    if (mUserInfoDao != null) {
                        userInfos = mUserInfoDao.queryBuilder().orderDesc(UserInfoDao.Properties.Mobile).build().list();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtil.d(TAG, "getUserInfo DB error: " + e.getMessage());
                }
                LogUtil.d(TAG, "getUserInfoFrom DB: " + userInfos);
                if (callback != null) {
                    callback.onCallback(userInfos);
                }
            }
        });
    }


    public void addUserInfo(final UserInfo userInfo) {
        if (mUserInfoDao != null) {
            if (userInfo != null) {
//                UserInfo oldUserInfo = getUserInfoByID(userInfo.getUser_id());
//                if (oldUserInfo != null) {
//                    userInfo.setId(oldUserInfo.getId());
//                }
                long result = mUserInfoDao.insertOrReplace(userInfo);
                LogUtil.d(TAG, "userInfo result: " + result);
            }
        }
    }

    public void deleteAllUserInfo(final long myUserId) {
        if (mUserInfoDao != null) {
            mUserInfoDao.deleteByKey(myUserId);
        }
    }

    public void deleteAllUserInfo() {
        if (mUserInfoDao != null) {
            mUserInfoDao.deleteAll();
        }
    }


}
