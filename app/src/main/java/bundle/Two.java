/*
 * Copyright (C) 2015 Baidu, Inc. All Rights Reserved.
 */
package bundle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by panhongchao on 15/11/24.
 */
public class Two extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText(getIntent().getExtras().getInt("test") + "");
        setContentView(textView);
    }
}
