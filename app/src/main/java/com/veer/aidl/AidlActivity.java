package com.veer.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AidlActivity extends AppCompatActivity {
    private AidlService.MyBinder mMyBinder;
    private TextView mTvClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        setTitle("服务端");
        mTvClient = (TextView) findViewById(R.id.tv_client);
        Intent intent = new Intent(this,AidlService.class);
        bindService(intent, mSCoon, BIND_AUTO_CREATE);
    }


    private ServiceConnection mSCoon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (AidlService.MyBinder) IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mSCoon = null;
        }
    };
    public void setText(String s){
        mTvClient.setText(s);
    }

}
