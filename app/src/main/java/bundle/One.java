/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import ipc.MyApp;

/**
 * Created by panhongchao on 15/11/24.
 */
public class One extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApp.count = 10;
        Button button = new Button(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(One.this, Two.class);
                Bundle bundle = new Bundle();
                bundle.putInt("test", MyApp.count);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        setContentView(button);
    }
}
