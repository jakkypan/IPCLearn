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
import java.net.Socket;
import java.util.Random;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by panhongchao on 15/12/6.
 */
public class TCpClientActivity extends AppCompatActivity {
    private Socket mClientSocket;

    private PrintWriter mPrintWriter;

    private boolean isEstablish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service = new Intent(TCpClientActivity.this, TCPServerService.class);
                startService(service);
            }
        });

        HandlerThread handlerThread = new HandlerThread("c-socket");
        handlerThread.start();
        new Handler(handlerThread.getLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                connectTCPServer();
            }
        }, 500);

        setContentView(button);
    }

    @Override
    protected void onDestroy() {
        isEstablish = false;
        mPrintWriter.close();
        if (mClientSocket != null) {
            try {
                mClientSocket.shutdownInput();
                mClientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onDestroy();
    }

    private void connectTCPServer() {
        while (mClientSocket == null) {
            Log.e("socket", "start to connect to server...");
            try {
                mClientSocket = new Socket("127.0.0.1", 8688);
                mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(mClientSocket
                        .getOutputStream())));
                isEstablish = true;
                Log.e("socket", "success to connect server!");

                // 定时发送信息过去
                HandlerThread handlerThread = new HandlerThread("send-socket");
                handlerThread.start();
                new Handler(handlerThread.getLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        while (isEstablish) {
                            mPrintWriter.println("client msg " + new Random().nextInt(100));
                            SystemClock.sleep(1000);
                        }
                    }
                }, 500);
            } catch (Exception e) {
                e.printStackTrace();
                SystemClock.sleep(1000);
                connectTCPServer();
            }

            // 接受server的信息
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
                while(!isFinishing()) {
                    String msg = br.readLine();
                    Log.e("socket", "server msg: " + msg);
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}