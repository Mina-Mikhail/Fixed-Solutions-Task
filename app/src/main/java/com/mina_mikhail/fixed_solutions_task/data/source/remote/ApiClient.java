package com.mina_mikhail.fixed_solutions_task.data.source.remote;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mina_mikhail.fixed_solutions_task.utils.Constants;
import java.util.concurrent.TimeUnit;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

  private volatile static ApiClient INSTANCE;

  private ApiInterface apiService;

  private ApiClient() {
    Interceptor headersInterceptor = chain -> {
      Request newRequest = chain.request();

      HttpUrl url = newRequest
          .url()
          .newBuilder()
          .addQueryParameter("api_key", Constants.API_KEY)
          .build();

      newRequest = newRequest
          .newBuilder()
          .url(url)
          .build();

      return chain.proceed(newRequest);
    };

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
        .readTimeout(120, TimeUnit.SECONDS)
        .connectTimeout(120, TimeUnit.SECONDS)
        .addInterceptor(headersInterceptor)
        .addNetworkInterceptor(new StethoInterceptor())
        .build();

    Retrofit retrofit = new Retrofit.Builder()
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .build();

    apiService = retrofit.create(ApiInterface.class);
  }

  public static ApiClient getInstance() {
    if (INSTANCE == null) {
      synchronized (ApiClient.class) {
        if (INSTANCE == null) {
          INSTANCE = new ApiClient();
        }
      }
    }
    return INSTANCE;
  }

  public ApiInterface getApiService() {
    return apiService;
  }
}