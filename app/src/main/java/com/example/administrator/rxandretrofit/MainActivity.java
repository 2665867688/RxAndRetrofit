package com.example.administrator.rxandretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.rxandretrofit.bean.Root;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnGet, mBtnGet2,mBtnGet3;
    private TextView mTvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnGet = (Button) findViewById(R.id.btn_get);
        mBtnGet.setOnClickListener(this);
        mBtnGet2 = (Button) findViewById(R.id.btn_get2);
        mBtnGet2.setOnClickListener(this);
        mBtnGet3 = (Button) findViewById(R.id.btn_get3);
        mBtnGet3.setOnClickListener(this);
        mTvShow = (TextView) findViewById(R.id.tv_show);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get://普通String数据
                Toast.makeText(this,"String 百度",Toast.LENGTH_SHORT).show();
                getRequest();
                break;
            case R.id.btn_get2://普通String数据
                Toast.makeText(this,"String 豆瓣",Toast.LENGTH_SHORT).show();
                getDouBan();
                break;
            case R.id.btn_get3://实体类数据
                Toast.makeText(this,"实体类",Toast.LENGTH_SHORT).show();
                getDbGson();
                break;
        }
    }

    /**
     * String
     */
    private void getRequest() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com")
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                })
                .build();
        ApiService ApiService = retrofit.create(ApiService.class);
        Call<String> call = ApiService.getBaidu();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, final Response<String> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvShow.setText(response.body().toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "lll", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * String
     */
    private void getDouBan() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, String>() {
                            @Override
                            public String convert(ResponseBody value) throws IOException {
                                return value.string();
                            }
                        };
                    }
                })
                .build();
        ApiService ApiService = retrofit.create(ApiService.class);
        Call<String> call = ApiService.getSearchBooks("小王子", "", 0, 3);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, final Response<String> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvShow.setText(response.body().toString());
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("请求失败", "请求失败");
                Toast.makeText(MainActivity.this, "lll", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 实体类
     */
    private void getDbGson(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                /*
                转换器，接口返回json数据会自动转换成转换器所要转换的数据，GsonConverterFactory
                会将json数据转换成实体类，response.body（）里携带的也将是实体类
                 */
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService ApiService = retrofit.create(ApiService.class);
        Call<Root> call = ApiService.getSearchBooks2("小王子", "", 0, 3);
        call.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, final Response<Root> response) {
                Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvShow.setText("作者："+response.body().getBooks().get(0).getAuthor()
                        +"\n" + "文章内容：\n" + response.body().getBooks().get(0).getSummary());
                    }
                });
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
