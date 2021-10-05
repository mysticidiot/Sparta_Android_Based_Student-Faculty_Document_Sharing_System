package com.mysticidiot.project_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class ttActvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tt_actvity);
        View web =(WebView)findViewById(R.id.webview);
        ((WebView) web).loadUrl("file:///android_asset/tt.html");

    }
}
