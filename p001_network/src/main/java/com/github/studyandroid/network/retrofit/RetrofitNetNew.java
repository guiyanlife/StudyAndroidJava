package com.github.studyandroid.network.retrofit;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetNew {
    private static RetrofitNetNew sInstance;
    private static Retrofit retrofit;
    private OkHttpClient client;

    public Retrofit init() {
        // 添加一个log拦截器,打印所有的log
        //HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // 可以设置请求过滤的水平,body,basic,headers
        //httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //设置 请求的缓存的大小跟位置
        File cacheFile = new File(getApp().getCacheDir(), "retrofit");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb 缓存的大小
        client = new OkHttpClient
                .Builder()
                .addInterceptor(addQueryParameterInterceptor()) //添加公共参数
                .addInterceptor(addHeaderInterceptor())         //添加header。例如，token过滤等
                .addInterceptor(new LoggingInterceptor())       //添加Log拦截器，用于打印请求与响应的日志
                .cache(cache)  //添加缓存
                .connectTimeout(30L, TimeUnit.SECONDS)
                .readTimeout(30L, TimeUnit.SECONDS)
                .writeTimeout(30L, TimeUnit.SECONDS)
                .build();

        //client.dispatcher().runningCalls().get(0).request().tag()

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;

    }

    private RetrofitNetNew() {
        init();
    }

    public static void config() {
        sInstance = new RetrofitNetNew();
    }

    public static RetrofitNetNew getInstance() {
        return sInstance;
    }

    /**
     * 获取网络框架
     *
     * @return
     */
    public Retrofit get() {
        return retrofit;
    }

    /**
     * 创建一个业务请求
     *
     * @param convertClass
     * @param <T>
     * @return
     */
    public static <T> T build(Class<T> convertClass) {
        return getInstance().get().create(convertClass);
    }

    /**
     * 创建一个业务请求
     *
     * @param convertClass
     * @param tag
     * @param <T>
     * @return
     */
    public static <T> T build(Class<T> convertClass, Object tag) {
        return getInstance().get().create(convertClass);
//        return (T) Proxy.newProxyInstance(convertClass.getClassLoader(),
//                new Class<?>[] {convertClass}, new Handler(tag));
    }

    public void cancelAll() {
        client.dispatcher().cancelAll();

    }

    public void cancel(Object tag) {
        Dispatcher dispatcher = client.dispatcher();
        synchronized (dispatcher) {
            for (Call call : dispatcher.queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : dispatcher.runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    /**
     * 请求参数拦截器，在URL中增加请求参数
     *     http://localhost/param?phoneSystem=13246363399&phoneModel=mi
     *
     * @return 请求参数拦截器
     */
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        //.addQueryParameter("phoneSystem", "13246363399")
                        //.addQueryParameter("phoneModel", "mi")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
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
                        //.header("token", "weqch3cr478c873noewcq7rcquigw34rfhyw")
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    /**
     * Log拦截器，用于打印请求与响应的日志
     */
    private class LoggingInterceptor implements Interceptor {
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
