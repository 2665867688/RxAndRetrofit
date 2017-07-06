package com.example.administrator.rxandretrofit.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.rxandretrofit.BaseActivity;
import com.example.administrator.rxandretrofit.R;
import com.example.administrator.rxandretrofit.bean.Root;
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
                //设置请求参数
                Map<String, Object> params = new HashMap<>();
                params.put("q", "小王子");
                params.put("tag", "");
                params.put("start", 0);
                params.put("count", 3);
                Observable<Root> observable = mServiceGson.getSearchBooks6(params);
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
        Root result = (Root) data;
        mTvShow.setText("标题：" + result.getBooks().get(0).getTitle() + "\n"
                + "作者：" + result.getBooks().get(0).getAuthor()
                + "\n" + "   " + result.getBooks().get(0).getSummary());
    }

    @Override
    protected <T> void requestFail(T data, RequestType type) {
        showToast("服务器返回错误");
    }


}
