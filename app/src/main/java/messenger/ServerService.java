/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by panhongchao on 15/11/24.
 */
public class ServerService extends Service {
    class MessageHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
//            Log.e("app", msg.getData().getString("test"));
            Toast.makeText(getApplicationContext(), msg.getData().getString("test"), Toast.LENGTH_LONG).show();

            Messenger reply = msg.replyTo;
            Message message = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.putString("reply", "this is server!");
            message.setData(bundle);
            try {
                reply.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }

    Messenger mMessenger = new Messenger(new MessageHandler());
}
