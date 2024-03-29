package com.github.studyandroid.network.retrofit.api;


import com.github.studyandroid.network.retrofit.bean.SearchProductsBean;
import com.github.studyandroid.network.util.NetConfigUtil;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductService {
    //POST
    //https://www.zhaoapi.cn/product/searchProducts?key1=val1&key2=val2&key3=val3&key4=val4
    @FormUrlEncoded
    @POST(NetConfigUtil.SERVER_ZHAO + "product/searchProducts")
    Call<ResponseBody> getProducts(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST(NetConfigUtil.SERVER_ZHAO + "product/{action}")
    Call<SearchProductsBean> getBeanProducts(@Path("action") String action, @Field("keywords") String value);
}
