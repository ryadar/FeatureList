package com.featurelist;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class NewActivationActivity extends AppCompatActivity implements NewActivationService.Callbacks {
    private EditText activationTitle;
    private String activationTitleName;
    private Button checkButton;
    private ListView listView;
    public  Intent serviceIntent;
    public NewActivationService myService;
    public boolean isServiceConnected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activation_activity);
        activationTitle = (EditText) findViewById(R.id.editText);
        checkButton = (Button) findViewById(R.id.button);
        listView =(ListView)findViewById(R.id.listView);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activationTitleName= activationTitle.getText().toString();
                if(activationTitleName!=null && activationTitleName.length()==0){
                    Logger.log("onClick-failed");
                    Toast.makeText(getApplicationContext(),"Not Empty",Toast.LENGTH_LONG).show();
                }
                else if(activationTitleName!=null ){
                    Logger.log("onClick-success");
                    if(myService==null){
                        serviceIntent= new Intent(NewActivationActivity.this,NewActivationService.class);
                        serviceIntent.putExtra(Constant.ACTIVATION_STRING,activationTitleName);
                        bindService(serviceIntent, newActivationServiceConnection,Context.BIND_AUTO_CREATE);
                    }else{
                        myService.startProcess(activationTitleName);
                    }
                }
            }
        });
    }
    ServiceConnection newActivationServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.log("onServiceConnected");
            NewActivationService.LocalBinder binder = (NewActivationService.LocalBinder) iBinder;
            myService = binder.getServiceInstance();
            myService.registerClient(NewActivationActivity.this);
            myService.startProcess(activationTitleName);
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
    public void success(Cursor cursor) {
        Toast.makeText(getApplicationContext(),"Activation Success",Toast.LENGTH_LONG).show();
        listView.setVisibility(View.VISIBLE);
        MyListAdapter todoAdapter = new MyListAdapter(NewActivationActivity.this, cursor);
        listView.setAdapter(todoAdapter);
    }
    @Override
    public void failure(String message) {
        listView.setVisibility(View.INVISIBLE);
        Toast.makeText(getApplicationContext(),"Activation failed",Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onStop() {
        if(isServiceConnected) {
            unbindService(newActivationServiceConnection);
            stopService(serviceIntent);
        }
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
