package com.veer.aidl.client;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.veer.aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    IMyAidlInterface iMyAidlInterface;
    private TextView mTvService;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("客户端");
        mTvService = (TextView) findViewById(R.id.tv_service);
        //绑定服务
        Intent intent = new Intent();
        intent.setAction("com.veer.aidl.MyService");
        intent.setPackage("com.veer.aidl");
        bindService(intent, conn, BIND_AUTO_CREATE);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

    }
    /**
     * 点击“AIDL”按钮事件
     */
    public void add() {
        try {
            if(iMyAidlInterface!=null){
                int res = iMyAidlInterface.add(1, 2);
                mTvService.setText("从服务端调用成功的结果：" + res);
                Log.i("client", "从服务端调用成功的结果：" + res);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 服务回调方法
     */
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iMyAidlInterface = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务，回收资源
        unbindService(conn);
    }

}
