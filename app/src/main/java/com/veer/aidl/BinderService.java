package com.veer.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/8/28
 */

public class BinderService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder{

        public void setMessage(String s,BinderActivity.CallBack callBack){
            Log.i("Binder_Service", s );
            callBack.sendMsg("从服务端发来的Binder响应");
        }
    }

}
