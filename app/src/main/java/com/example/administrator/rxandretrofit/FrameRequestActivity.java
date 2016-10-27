package com.example.administrator.rxandretrofit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxandretrofit.constant.RequestType;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

import static com.example.administrator.rxandretrofit.constant.RequestType.test;

public class FrameRequestActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnRequest;
    private TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_request);
        initView();
    }

    private void initView() {
        mBtnRequest = (Button) findViewById(R.id.btn_frt_request);
        mBtnRequest.setOnClickListener(this);
        mTvShow = (TextView) findViewById(R.id.tv_frt_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_frt_request:
                Observable<String> observable = mServiceString.getSearchBooks5("小王子", "", 0, 3);
                sendRequest(observable, RequestType.test);
                break;
        }
    }

    /**
     * 请求返回参数
     *
     * @param data
     * @param type
     * @param <T>
     */
    @Override
    protected <T> void requestSuccess(T data, RequestType type) {
        switch (type) {
            case test:
                String datas = data.toString();
                mTvShow.setText(datas);
                break;
            default:
                break;
        }
    }

    @Override
    protected <T> void requestFail(T data, RequestType type) {
        switch (type) {
            case test:
                String datas = data.toString();
                mTvShow.setText(datas);
                break;
            default:
                break;
        }
    }


}
