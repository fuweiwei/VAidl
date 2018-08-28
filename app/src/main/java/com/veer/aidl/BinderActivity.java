package com.veer.aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class BinderActivity extends AppCompatActivity {
    private BinderService.MyBinder mMyBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder);
        setTitle("客户端");
        //绑定服务
        Intent intent = new Intent(BinderActivity.this,BinderService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyBinder.setMessage("从客服端发来的Binder请求",new CallBack() {
                    @Override
                    public void sendMsg(String s) {
                        Log.i("Binder_Client", s );
                    }
                });
            }
        });
    }
    /**
     * 服务回调方法
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyBinder = (BinderService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMyBinder = null;
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务，回收资源
        unbindService(conn);
    }

   public interface CallBack{
        void sendMsg(String s);
    }
}
