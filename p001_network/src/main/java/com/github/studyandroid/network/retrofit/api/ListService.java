package com.github.studyandroid.network.retrofit.api;


import com.github.studyandroid.network.retrofit.bean.GetListBean;
import com.github.studyandroid.network.retrofit.net.NetConfig;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ListService {
    @GET(NetConfig.SERVER_GITHUB + "users/list")
    Call<ResponseBody> getList();

    @GET(NetConfig.SERVER_GITHUB + "users/{user}")
    Call<ResponseBody> getList(@Path("user") String user);

    @GET(NetConfig.SERVER_GITHUB + "users/{user}")
    Call<GetListBean> getBeanList(@Path("user") String user);
}

