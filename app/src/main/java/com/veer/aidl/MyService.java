package com.veer.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/8/24
 */

public class MyService extends Service {
    private String mClient;
    private MainActivity mMainActivity;
    private MyHandler mMyHandler = new MyHandler();

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    mMainActivity.setText(mClient);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends IMyAidlInterface.Stub {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.i("service", "从客户端发来的AIDL请求:num1->" + num1 + "::num2->" + num2);
            mClient = "从客户端发来的AIDL请求:num1->" + num1 + "___num2->" + num2;
            mMyHandler.sendEmptyMessage(0);
            return num1+num2;
        }
        public void setActivity(MainActivity mainActivity){
            mMainActivity = mainActivity;
        }
    };
}
