package com.featurelist;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;

public class ActivatedFeaturesListService extends Service {
    public Callbacks activity;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private final IBinder mBinder = new LocalBinder();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.log("onStartCommand");
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Logger.log("onCreate");
        super.onCreate();
    }
    void getData(){
            Logger.log("startProcess");
            dbHelper = new MySQLiteHelper(getApplicationContext());
            database = dbHelper.getWritableDatabase();
            Cursor todoCursor = database.rawQuery("SELECT  * FROM feature_list", null);
            activity.updateData(todoCursor);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.log("onBind");
        return mBinder;
    }
    public class LocalBinder extends Binder {
        public ActivatedFeaturesListService getServiceInstance(){
            return ActivatedFeaturesListService.this;
        }
    }
    public void connect(Activity activity){
        this.activity = (Callbacks)activity;
    }
    public interface Callbacks{
        void updateData(Cursor cursor);
    }
}
