package com.github.studyandroid.network.okhttp;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.github.studyandroid.network.retrofit.RetrofitNetNew;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

public class OkHttpNew {
    private static long CONNECTION_TIMEOUT = 20L; //Connection timeout 20s
    private static long READ_TIMEOUT = 20L;       //Read timeout 30s
    private static long WRITE_TIMEOUT = 20L;      //Write timeout 20s
    private static long CACHE_MAX_SIZE = 1024 * 1024 * 50; //Cache max size is 50Mb
    private static String[] VERIFY_HOST_NAME_ARRAY = new String[]{};   //hostname list
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final Map<String, String> DEFAULT_REQUEST_HEADER = new HashMap<>();
    private static OkHttpClient mHttpClient = null;


    /**
     * 创建OkHttp Client
     *
     * @return OkHttp Client
     */
    public static OkHttpClient createOkHttpClient() {
        if (mHttpClient != null) {
            return mHttpClient;
        }
        //设置 请求的缓存的大小跟位置
        File cacheFile = new File(getApp().getCacheDir(), "okhttp");
        Cache cache = new Cache(cacheFile, CACHE_MAX_SIZE);
        mHttpClient = new OkHttpClient
                .Builder()
                .cache(cache)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(addHeaderInterceptor())  //添加header。例如，SN号、时间戳等
                .hostnameVerifier(addHostnameVerifier()) //增加hostname验证，即那些IP拦截
                .addInterceptor(new LoggingInterceptor())       //添加Log拦截器，用于打印请求与响应的日志
                .build();
        return mHttpClient;
    }

    /**
     * 创建请求体
     * @param url 请求的地址
     * @param headers 请求的headers
     * @param method 请求的方法
     * @param body 请求的body
     * @return 返回请求体
     */
    public static Request createrRequest(String url, Map<String, String> headers, OkHttpNew.Method method, RequestBody body) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (headers == null || headers.size() == 0) {
        } else {
            Set<String> keys = headers.keySet();
            for (String key : keys) {
                builder.header(key, headers.get(key));
            }
        }
        builder.method(httpMethodConvert(method), body);

        return builder.build();
    }

    /**
     * 创建客户端的Call
     * @param method 请求的方法
     * @param url 请求的地址
     * @param data 请求的json格式数据
     * @return 返回Call
     */
    public static Call httpNewCall(OkHttpNew.Method method, String url, String data) {
        OkHttpClient client = OkHttpNew.createOkHttpClient();
        RequestBody body = null;
        if (data  != null) {
            body = RequestBody.create(MEDIA_TYPE_JSON, data);
        }
        Request request = OkHttpNew.createrRequest(url, OkHttpNew.DEFAULT_REQUEST_HEADER, method, body);
        return client.newCall(request);
    }

    /**
     * json字符串转换为javabean
     * @param json json字符串
     * @param classT JavaBean.class
     * @param <T> JavaBean.class的类
     * @return json转换后额javabean
     */
    public static <T> T jsonToObject(String json, Class<T> classT) {
        Gson gson = new Gson();
        T object = gson.fromJson(json, classT);
        return object;
    }

    /**
     * JavaBean转换为json字符串
     * @param obj JavaBean的实例
     * @return JavaBean对应的json字符串
     */
    public static String objectToJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * 请求方法的枚举类型
     */
    public enum Method {
        GET,
        POST
    }

    /**
     * 请求方法转换成对应的字符串
     * @param method 请求方法
     * @return 请求方法对应的字符串
     */
    private static String httpMethodConvert(OkHttpNew.Method method) {
        switch (method) {
            case GET:
                return "GET";
            case POST:
                return "POST";
            default:
                return "POST";
        }
    }

    /**
     * Header拦截器，在请求中增加Header内容
     *
     * @return Header拦截器
     */
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
                        .header("sn", Build.SERIAL)
                        //.header("User-Agent", "bb-device")
                        //.header("Timestamp", String.valueOf(System.currentTimeMillis()))
                        //.header("Sign", "qewc2123wet7by9trnj315u")
                        //.header("terminalVersion", "2.3")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * hostname验证，在VERIFY_HOST_NAME_ARRAY设置哪些IP被拦截
     *
     * @return hostname验证实例
     */
    private static HostnameVerifier addHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession sslSession) {
                if (TextUtils.isEmpty(hostname)) {
                    return false;
                }
                return !Arrays.asList(VERIFY_HOST_NAME_ARRAY).contains(hostname);
            }
        };
        return hostnameVerifier;
    }

    /**
     * Log拦截器，用于打印请求与响应的日志
     */
    private static class LoggingInterceptor implements Interceptor {
        private static final String TAG = "HTTP_LOG";
        private final Charset UTF8 = Charset.forName("UTF-8");

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            RequestBody requestBody = request.body();
            String body = null;
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                body = buffer.readString(charset);
            }
            Log.d(TAG, String.format("Send request\nmethod: %s\nurl: %s\nheaders: %s\nbody: %s", request.method(), request.url(), request.headers(), body));
            long startNs = System.nanoTime();
            Response response = chain.proceed(request);
            long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
            ResponseBody responseBody = response.body();
            String rBody = null;
            if (HttpHeaders.hasBody(response)) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                Charset charset = UTF8;
                MediaType contentType = responseBody.contentType();
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8);
                    } catch (UnsupportedCharsetException e) {
                        e.printStackTrace();
                    }
                }
                rBody = buffer.clone().readString(charset);
            }
            Log.d(TAG, String.format("Receive response\ncode: %s\nmessage: %s\ntime: %sms\nbody: %s", response.code(), response.message(), tookMs, rBody));
            return response;
        }
    }

    /**
     * 通过反射机制，获取当前APP的Application
     */
    private static Application sInstanceApp;

    private static Application getApp() {
        if (sInstanceApp == null) {
            Application app = null;
            try {
                app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
                if (app == null) {
                    throw new IllegalStateException("Static initialization of Applications must be on main thread.");
                }
            } catch (final Exception e) {
                try {
                    app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
                } catch (final Exception ex) {
                    e.printStackTrace();
                }
            } finally {
                sInstanceApp = app;
            }
        }
        return sInstanceApp;
    }
}
