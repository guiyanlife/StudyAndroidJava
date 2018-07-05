package com.github.studyandroid.retrofit;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.ArrayMap;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.commonlibs.libretrofit.RetrofitNetNew;
import com.github.studyandroid.retrofit.api.ListService;
import com.github.studyandroid.retrofit.api.ProductService;
import com.github.studyandroid.retrofit.bean.GetListBean;
import com.github.studyandroid.retrofit.bean.SearchProductsBean;

import java.io.IOException;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int MSG_UPDATA_TEXTVIEW = 0;

    private ListService mListService;
    private ProductService mProductService;

    private TextView mResponseTextview;
    private Button mGetButton;
    private Button mGetuserButton;
    private Button mGetgsonButton;
    private Button mPostButton;
    private Button mPostGsonButton;
    private Button mExitButton;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATA_TEXTVIEW:
                    mResponseTextview.scrollTo(0, 0);
                    mResponseTextview.setText((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResponseTextview = findViewById(R.id.response_textview);
        mGetButton = findViewById(R.id.get_button);
        mGetgsonButton = findViewById(R.id.getgson_button);
        mGetuserButton = findViewById(R.id.getuser_button);
        mPostButton = findViewById(R.id.post_button);
        mPostGsonButton = findViewById(R.id.postgson_button);
        mExitButton = findViewById(R.id.exit_button);

        mGetButton.setOnClickListener(this);
        mGetgsonButton.setOnClickListener(this);
        mGetuserButton.setOnClickListener(this);
        mPostButton.setOnClickListener(this);
        mPostGsonButton.setOnClickListener(this);
        mExitButton.setOnClickListener(this);

        mResponseTextview.setMovementMethod(ScrollingMovementMethod.getInstance());

        mListService = RetrofitNetNew.getInstance().get().create(ListService.class);
        mProductService = RetrofitNetNew.build(ProductService.class);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_button:
                get();
                break;
            case R.id.getuser_button:
                getuser("guiyanwork");
                break;
            case R.id.getgson_button:
                getgson("list1");
                break;
            case R.id.post_button:
                post();
                break;
            case R.id.postgson_button:
                postgson();
                break;
            case R.id.exit_button:
                exit();
                break;
            default:
                break;
        }
    }

    private void get() {
        Call<ResponseBody> call = mListService.getList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message = new Message();
                message.what = MSG_UPDATA_TEXTVIEW;
                try {
                    message.obj = response.body().string();
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getuser(String user) {
        Call<ResponseBody> call = mListService.getList(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message = new Message();
                message.what = MSG_UPDATA_TEXTVIEW;
                try {
                    message.obj = response.body().string();
                    mHandler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable  t) {
                t.printStackTrace();
            }
        });
    }

    private void getgson(String user) {
        Call<GetListBean> call = mListService.getBeanList(user);
        call.enqueue(new Callback<GetListBean>() {
            @Override
            public void onResponse(Call<GetListBean> call, Response<GetListBean> response) {
                Message message = new Message();
                message.what = MSG_UPDATA_TEXTVIEW;
                message.obj = response.body().toString();
                mHandler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<GetListBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void post() {
        Map<String, String> map = new ArrayMap<>();
        map.put("keywords", "mi");
        RetrofitNetNew.build(ProductService.class).getProducts(map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Message message = new Message();
                message.what = MSG_UPDATA_TEXTVIEW;
                try {
                    message.obj = response.body().string();
                    mHandler.sendMessage(message);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void postgson(){
        mProductService.getBeanProducts("searchProducts", "笔记本").enqueue(new Callback<SearchProductsBean>() {
            @Override
            public void onResponse(Call<SearchProductsBean> call, Response<SearchProductsBean> response) {
                Message message = new Message();
                message.what = MSG_UPDATA_TEXTVIEW;
                message.obj = response.body().toString();
                mHandler.sendMessage(message);
            }

            @Override
            public void onFailure(Call<SearchProductsBean> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void exit() {
        this.finish();
    }

}
