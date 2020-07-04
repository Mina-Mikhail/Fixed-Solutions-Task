package com.mina_mikhail.fixed_solutions_task.di.module;

import com.mina_mikhail.fixed_solutions_task.BuildConfig;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OkHttpClientModule {

  private static final long REQUEST_TIMEOUT = 60;
  private static final String API_KEY = "bf6840f7825e215d725f507d56d176c0";

  /**
   * @return a singleton instance of OkHttpClient
   */
  @Provides
  @Singleton
  public OkHttpClient okHttpClient(Interceptor headerInterceptor,
      HttpLoggingInterceptor httpLoggingInterceptor) {
    return new OkHttpClient()
        .newBuilder()
        .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
        .addNetworkInterceptor(headerInterceptor)
        .addNetworkInterceptor(httpLoggingInterceptor)
        .retryOnConnectionFailure(false)
        .build();
  }

  /**
   * @return a logger instance to log all network requests and its responses
   */
  @Provides
  public HttpLoggingInterceptor httpLoggingInterceptor() {
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
    httpLoggingInterceptor.level(
        BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BASIC);
    return httpLoggingInterceptor;
  }

  /**
   * @return a header Interceptor used to add custom header with all network requests
   */
  @Provides
  @Singleton
  public Interceptor provideInterceptor() {
    return chain -> {
      Request newRequest = chain.request();

      HttpUrl url = newRequest
          .url()
          .newBuilder()
          .addQueryParameter("api_key", API_KEY)
          .build();

      newRequest = newRequest
          .newBuilder()
          .url(url)
          .build();

      return chain.proceed(newRequest);
    };
  }
}
