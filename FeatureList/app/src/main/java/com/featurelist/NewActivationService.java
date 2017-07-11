package com.featurelist;

import android.app.Activity;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import java.util.ArrayList;


public class NewActivationService extends Service {
    public Callbacks activity;
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private final IBinder mBinder = new LocalBinder();
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.log("onStartCommand");
        String activationString=intent.getExtras().getString(Constant.ACTIVATION_STRING);
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        Logger.log("onCreate");
        super.onCreate();
    }
    void startProcess(String activationString){
        Logger.log("startProcess");
        if(activationString.equals("primary")){
            Logger.log("primary");
            ArrayList<FeatureListItem> itemList = createMockValues();
            if(!Constant.IS_DB_CREATED){
                Constant.IS_DB_CREATED=true;
                dbHelper = new MySQLiteHelper(getApplicationContext());
                database = dbHelper.getWritableDatabase();
                saveMockData(createMockValues());
            }

            Cursor todoCursor = database.rawQuery("SELECT  * FROM feature_list", null);


            activity.success(todoCursor);
        }else{
            activity.failure("Activation Failed");
        }
    }
    public void saveMockData(ArrayList<FeatureListItem> items) {
        for(FeatureListItem item:items){
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.NAME_COLUMN,item.getFeatureTitle());
            values.put(MySQLiteHelper.STATUS_COLUMN,item.getFeatureStatus());
            values.put(MySQLiteHelper.TIME_LEFT_COLUMN,item.getTimeLeft());
            values.put(MySQLiteHelper.CATEGORY_COLUMN,item.getFeatureCategory());
            long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,values);
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        Logger.log("onBind");
        return mBinder;
    }
    public class LocalBinder extends Binder {
        public NewActivationService getServiceInstance(){
            return NewActivationService.this;
        }
    }
    public void registerClient(Activity activity){
        this.activity = (Callbacks)activity;
    }
    public interface Callbacks{
        public void success(Cursor cursor);
        public void failure(String message);
    }
    ArrayList<FeatureListItem> createMockValues(){
        ArrayList<FeatureListItem> itemList = new ArrayList<>();
        FeatureListItem  item1 = new FeatureListItem("Feature 1", FeaturesResults.STATUS_PERMANENT, 20, FeaturesResults.PRIMARY_FEATURE);
        FeatureListItem  item2 = new FeatureListItem("Feature 2", FeaturesResults.STATUS_DEMO, 1497934800000L, FeaturesResults.PRIMARY_FEATURE);
        FeatureListItem  item3 = new FeatureListItem("Feature 3", FeaturesResults.STATUS_DEMO, 1498194000000L, FeaturesResults.PRIMARY_FEATURE);
        FeatureListItem  item4 = new FeatureListItem("Feature 4", FeaturesResults.STATUS_NOT_ACTIVATED, 1498194000000L, FeaturesResults.PRIMARY_FEATURE);
        FeatureListItem item5 = new FeatureListItem("Feature 5", FeaturesResults.STATUS_DEMO, 1498194000000L, FeaturesResults.PRIMARY_FEATURE);
        FeatureListItem item6 = new FeatureListItem("Feature 6", FeaturesResults.STATUS_PERMANENT, 1498194000000L, FeaturesResults.PRIMARY_FEATURE);
        FeatureListItem item7 = new FeatureListItem("Feature 7", FeaturesResults.STATUS_DEMO, 1498194000000L, FeaturesResults.PRIMARY_FEATURE);
        FeatureListItem item8 = new FeatureListItem("Feature 8", FeaturesResults.STATUS_NOT_ACTIVATED, 1498194000000L, FeaturesResults.PRIMARY_FEATURE);
        itemList.add(item1);
        itemList.add(item2);
        itemList.add(item3);
        itemList.add(item4);
        itemList.add(item5);
        itemList.add(item6);
        itemList.add(item7);
        itemList.add(item8);
        return  itemList;
    }
}
