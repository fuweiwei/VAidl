package com.veer.aidl.client;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MessengerActivity extends AppCompatActivity {
    private Messenger mMessenger;
    private Messenger mMyClientMessenger = new Messenger(new MyClientHandler());

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        setTitle("客服端");
        Intent intent = new Intent();
        intent.setAction("com.veer.aidl.MessengerService");
        intent.setPackage("com.veer.aidl");
        bindService(intent, mCoon, BIND_AUTO_CREATE);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Message message = Message.obtain(null,0);
                    message.replyTo = mMyClientMessenger;
                    mMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private  class  MyClientHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Log.i("Binder_client", "从服务端发来的Messenger请求，已经接受到通知");
                    break;
                default:

                    super.handleMessage(msg);
            }

        }
    }
    /**
     * 服务回调方法
     */
    private ServiceConnection mCoon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务，回收资源
        unbindService(mCoon);
    }

}
