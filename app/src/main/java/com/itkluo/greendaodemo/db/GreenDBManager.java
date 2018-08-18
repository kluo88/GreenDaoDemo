package com.itkluo.greendaodemo.db;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.itkluo.greendaodemo.db.help.MyOpenHelper;
import com.itkluo.greendaodemo.greendao.DaoMaster;
import com.itkluo.greendaodemo.greendao.DaoSession;
import com.itkluo.greendaodemo.utils.LogUtil;

import java.io.File;
import java.io.IOException;


/**
 * [数据库管理类，数据采用GreenDao来实现，所有实现通过模板自动生成；通过获取daoSession来获取所有的dao，从而实现操作对象]
 **/
public class GreenDBManager {

    private final String TAG = "GreenDBManager";

    // 是否加密
    public static final boolean ENCRYPTED = true;

    private static final String DB_NAME = "demo.db";
    private static GreenDBManager mDbManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    private static boolean changeDBPath = false;
    private static final String DB_PATH = "myDemo/database";

    private Context mContext;


    private GreenDBManager(Context context) {
        this.mContext = context;
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
        getDaoMaster(context);
        getDaoSession(context);
    }

    public static GreenDBManager getInstance(Context context) {
        if (null == mDbManager) {
            synchronized (GreenDBManager.class) {
                if (null == mDbManager) {
                    mDbManager = new GreenDBManager(context);
                }
            }
        }
        return mDbManager;
    }

    /**
     * 获取可读数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * 获取可写数据库
     *
     * @param context
     * @return
     */
    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * 获取DaoMaster
     * <p>
     * 判断是否存在数据库，如果没有则创建数据库
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (null == mDaoMaster) {
            synchronized (GreenDBManager.class) {
                if (null == mDaoMaster) {
                    MyOpenHelper helper = getMyOpenHelper(context);

                    mDaoMaster = new DaoMaster(helper.getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    @NonNull
    private static MyOpenHelper getMyOpenHelper(final Context context) {
        if (!changeDBPath) {//修改DB存储路径
            return new MyOpenHelper(context, DB_NAME, null);
        }
        return new MyOpenHelper(new ContextWrapper(context) {

            @Override
            public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
                return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
            }

            @Override
            public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
                return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), null);
            }

            @Override
            public File getDatabasePath(String name) {
                File file = buildDataBasePath(DB_PATH, name);
                return file != null ? file : super.getDatabasePath(name);
            }
        }, DB_NAME, null);
    }

    public static DaoSession getDaoSession(Context context) {
        if (null == mDaoSession) {
            synchronized (GreenDBManager.class) {
                mDaoSession = getDaoMaster(context).newSession();
            }
        }

        return mDaoSession;
    }


    public void uninit() {
        LogUtil.d(TAG, "GreenDBManager uninit");
        if (mDaoSession != null && mDaoSession.getDatabase() != null) {
            mDaoSession.getDatabase().close();
        }
        mDaoSession = null;
        mDaoMaster = null;
    }

    public static File buildDataBasePath(String dir, String name) {

        boolean sdExist = android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment.getExternalStorageState());
        if (!sdExist) {
            return null;
        } else {
            String dbDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
            String dbPath = dbDir + "/" + dir;// 数据库路径
            File dirFile = new File(dbPath);
            if (!dirFile.exists())
                dirFile.mkdirs();

            boolean isFileCreateSuccess = false;
            // 判断文件是否存在，不存在则创建该文件
            String filePath = dbPath + "/" + name;
            File dbFile = new File(filePath);
            if (!dbFile.exists()) {
                try {
                    isFileCreateSuccess = dbFile.createNewFile();// 创建文件
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                isFileCreateSuccess = true;
            }
            if (isFileCreateSuccess)
                return dbFile;
            else
                return null;
        }
    }
}
