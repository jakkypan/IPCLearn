/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package binderpool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by panhongchao on 15/12/8.
 */
public class BinderPoolService extends Service {

    private Binder mBinderPool = new BinderPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
