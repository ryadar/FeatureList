package com.featurelist;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

public class ActivatedFeaturesActivity extends AppCompatActivity implements ActivatedFeaturesListService.Callbacks{
    private ListView listView;
    public  Intent serviceIntent;
    public ActivatedFeaturesListService myService;
    public  boolean isServiceConnected=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.log("onCreate");
        setContentView(R.layout.activated_feature_activity);
        listView =(ListView)findViewById(R.id.listView);
        if(myService==null){
            serviceIntent= new Intent(ActivatedFeaturesActivity.this,ActivatedFeaturesListService.class);
            bindService(serviceIntent, activatedFeatureListServiceConnection,Context.BIND_AUTO_CREATE);
        }else{
            myService.getData();
        }
    }
    ServiceConnection activatedFeatureListServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.log("onServiceConnected");
            ActivatedFeaturesListService.LocalBinder binder = (ActivatedFeaturesListService.LocalBinder) iBinder;
            myService = binder.getServiceInstance();
            myService.connect(ActivatedFeaturesActivity.this);
            myService.getData();
            isServiceConnected=true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.log("onServiceDisconnected");
            myService=null;
            isServiceConnected=false;
        }
    };

    @Override
    protected void onStop() {
      if(isServiceConnected) {
          unbindService(activatedFeatureListServiceConnection);
          stopService(serviceIntent);
      }
        super.onStop();
    }

    @Override
    public void updateData(Cursor cursor) {
        listView.setVisibility(View.VISIBLE);
        MyListAdapter todoAdapter = new MyListAdapter(ActivatedFeaturesActivity.this, cursor);
        listView.setAdapter(todoAdapter);
    }
}
