package com.itkluo.greendaodemo.db;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * Created by luobingyong on 2018/8/17.
 */
public abstract class BaseCacheManager {

    protected Handler mWorkHandler;
    protected HandlerThread mWorkThread;
    protected static Handler mHandler;
    protected final Context mContext;


    public BaseCacheManager(Context context) {
        mContext = context;
        mHandler = new Handler(Looper.getMainLooper());
    }

    public abstract void openDB();

    public abstract void closeDB();

    /**
     * 泛型类，主要用于 API 中功能的回调处理。
     *
     * @param <T> 声明一个泛型 T。
     */
    public static abstract class ResultCallback<T> {

//        public  class Result<T> {
//            public T t;
//        }

        public ResultCallback() {

        }

        /**
         * 成功时回调。
         *
         * @param t 已声明的类型。
         */
        public abstract void onSuccess(T t);

//        /**
//         * 错误时回调。
//         *
//         * @param errString 错误提示
//         */
//        public abstract void onError(String errString);
//        public void onFail(final String errString) {
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
////                    onError(errString);
//                }
//            });
//        }

        /**
         * 切换回主线程
         *
         * @param t
         */
        public void onCallback(final T t) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(t);
                }
            });
        }
    }


}
