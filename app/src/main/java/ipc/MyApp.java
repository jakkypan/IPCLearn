/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package ipc;

import java.io.File;

import android.app.Application;
import android.util.Log;

/**
 * Created by panhongchao on 15/11/24.
 */
public class MyApp extends Application {
    public static int count = 0;

    public static File dir;

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = android.os.Process.myPid() + "";
        Log.e("app", processName);
    }
}
