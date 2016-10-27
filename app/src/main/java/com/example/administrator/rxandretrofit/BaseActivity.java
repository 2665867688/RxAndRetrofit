package com.example.administrator.rxandretrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.example.administrator.rxandretrofit.constant.RequestType;
import com.example.administrator.rxandretrofit.webservice.ApiService;
import com.example.administrator.rxandretrofit.webservice.HttpsUtils;
import com.google.gson.Gson;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/10/27.
 */

public abstract class BaseActivity extends AppCompatActivity {
    //返回String
    protected ApiService mServiceString;
    //返回实体类
    protected ApiService mServiceGson;
    protected Gson mGson = new Gson();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mServiceString = HttpsUtils.getInstance().createService();
        mServiceGson = HttpsUtils.getInstance().createServiceGson();
    }

    /**
     * dilog显示
     */
    private void showDialog() {
        showToast("加载中...");
    }

    private void hideDialog() {
        showToast("加载完成");
    }

    /**
     * 请求数据
     *
     * @param observable
     * @param type
     */

    protected <T> void sendRequest(Observable<T> observable, final RequestType type) {
        showDialog();
        observable
                // 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。
                .subscribeOn(Schedulers.io())
                // observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<T>() {
                    @Override
                    public void onCompleted() {
                        hideDialog();
                        showToast("请求成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        showToast("请求失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(T data) {
                        int result = 0;
                        if (result == 0)
                            requestSuccess(data, type);
                        else
                            requestFail(data, type);
                    }
                });

    }


    //请求成功
    protected abstract <T> void requestSuccess(T data, RequestType type);

    //请求失败
    protected abstract <T> void requestFail(T data, RequestType type);


    /**
     * 最后一个参数为true表示finish当前activity
     */
    public void startNextActivity(Bundle bundle, Class<?> pClass, boolean finishFlag) {
        Intent intent = new Intent(this, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        //跳转统一动画
//        overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
        if (finishFlag) {
            super.finish();
        }
    }

    /**
     * 请求activity for reslut
     */
    public void startNextActivityForResult(Bundle bundle, Class<?> pClass, int resquestCode) {
        Intent intent = new Intent(this, pClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, resquestCode);
//        overridePendingTransition(R.anim.enter_righttoleft, R.anim.exit_righttoleft);
    }

    /**
     * Toast
     *
     * @param text
     */
    protected void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    protected void showToastLong(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
