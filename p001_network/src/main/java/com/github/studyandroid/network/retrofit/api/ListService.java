package com.github.studyandroid.network.retrofit.api;


import com.github.studyandroid.network.retrofit.bean.GetListBean;
import com.github.studyandroid.network.util.NetConfigUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ListService {
    @GET(NetConfigUtil.SERVER_GITHUB + "users/list")
    Call<ResponseBody> getList();

    @GET(NetConfigUtil.SERVER_GITHUB + "users/{user}")
    Call<ResponseBody> getList(@Path("user") String user);

    @GET(NetConfigUtil.SERVER_GITHUB + "users/{user}")
    Call<GetListBean> getBeanList(@Path("user") String user);
}

