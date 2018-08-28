package com.veer.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
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

public class AidlService extends Service {

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
            Log.i("Aidl_Service", "从客户端发来的AIDL请求:num1->" + num1 + "::num2->" + num2);
            return num1+num2;
        }
    };
}
