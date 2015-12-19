/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by panhongchao on 15/12/6.
 */
public class TCPServerService extends Service {
    private boolean mIsServiceDestoryed = false;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new TcpServer()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed = true;
        super.onDestroy();
    }

    private class TcpServer implements Runnable {

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket(8688);
                Log.e("socket", "success to establish tcp service");
            } catch (IOException e) {
                Log.e("socket", "failed to establish tcp service");
                return;
            }

            try {
                while (!mIsServiceDestoryed) {
                    Log.e("socket", "start accept msg....");
                    Socket client = serverSocket.accept();

                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client
                            .getOutputStream())));
                    String str = in.readLine();
//                    if (str == null) {
//                        in.close();
//                        out.close();
//                        continue;
//                    }
                    Log.e("socket", "client msg: " + str);
                    out.println("server msg " + new Random().nextInt(100));
                    in.close();
                    out.close();
                }
            } catch (Exception e) {
                Log.e("socket", "accept msg error....");
                e.printStackTrace();
            }
        }
    }
}
