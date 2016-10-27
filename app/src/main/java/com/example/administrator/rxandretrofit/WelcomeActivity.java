package com.example.administrator.rxandretrofit;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.administrator.rxandretrofit.constant.Const;
import com.example.administrator.rxandretrofit.constant.LibConstant;

import java.util.Properties;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        startMain();
        new Thread(new Runnable() {
            @Override
            public void run() {
                initServiceUrl();
            }
        }).start();
    }

    private void startMain() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        }, 1100);
    }

    private void initServiceUrl() {
        Properties prop = new Properties();
        try {
            prop.load(getAssets().open("config.properties"));
            Const.sServiceUrl = prop.getProperty("webserver");
//            LibConstant.sServiceUrl = Const.sServiceUrl;
//            Const.DEBUG = "true".endsWith(prop.getProperty("debug"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
