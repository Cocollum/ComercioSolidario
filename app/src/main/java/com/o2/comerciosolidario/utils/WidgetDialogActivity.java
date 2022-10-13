package com.o2.comerciosolidario.utils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.o2.comerciosolidario.R;

public class WidgetDialogActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_privacy_policy);

        Button dismissbutton = (Button) findViewById(R.id.w_dismiss_btn);
        dismissbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                WidgetDialogActivity.this.finish();
            }
        });
    }
}
