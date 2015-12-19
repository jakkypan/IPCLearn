/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package aidl;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by panhongchao on 15/11/27.
 */
public class BookManagerActivity extends AppCompatActivity {
    private IBookManager mRemoteBookManager;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.e("book", "onServiceConnected: " + Thread.currentThread().toString());
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);
            try {
                // 绑定上死亡事件
                service.linkToDeath(mDeathRecipient, 0);

                mRemoteBookManager = iBookManager;
//                List<Book> list = iBookManager.getBookList();
//                Log.e("book", "book list size: " + list.size());
                mRemoteBookManager.registerListener(iOnNewBookArrivedListener);
                mRemoteBookManager.getBookList();
            } catch (Exception e) {

            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("book", "onServiceConnected: " + Thread.currentThread().toString());
            mRemoteBookManager = null;
            Log.e("book", "binder is died.");
        }
    };

    static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Log.e("book", "received new book: " + msg.obj.toString());
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            // 重连
            Intent intent = new Intent(BookManagerActivity.this, BookManagerService.class);
            bindService(intent, connection, BIND_AUTO_CREATE);
        }
    };

    private IOnNewBookArrivedListener iOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            mHandler.obtainMessage(0, newBook).sendToTarget();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookManagerActivity.this, BookManagerService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
            }
        });
        setContentView(button);
    }

    @Override
    protected void onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {
            try {
                mRemoteBookManager.unregisterListener(iOnNewBookArrivedListener);
                Log.e("book", "unregister listener");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(connection);
        super.onDestroy();
    }
}
