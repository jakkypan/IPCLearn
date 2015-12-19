/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package messenger;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by panhongchao on 15/11/24.
 */
public class Client extends AppCompatActivity {
    Messenger mService;

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("test", "this is client!");
            message.setData(bundle);
            message.replyTo = messenger;
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Client.this, ServerService.class);
                bindService(intent, connection, BIND_AUTO_CREATE);
            }
        });
        setContentView(button);
    }

    private Messenger messenger = new Messenger(new MessageHandler());

    class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), msg.getData().getString("reply"), Toast.LENGTH_SHORT).show();
            unbindService(connection);
        }
    }
}
