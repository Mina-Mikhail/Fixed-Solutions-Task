package com.mina_mikhail.fixed_solutions_task.di.module;

import android.util.Log;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = { OkHttpClientModule.class })
public class RetrofitModule {

  private static final String BASE_URL = "https://api.themoviedb.org/3/";

  /**
   * @return a Singleton object of Retrofit
   */
  @Singleton
  @Provides
  Retrofit provideRetrofit(OkHttpClient okHttpClient) {
    try {
      return getRetrofit(okHttpClient);
    } catch (Exception e) {
      Log.e("LogTag", "Bad Base Url Format");
      return getRetrofit(okHttpClient);
    }
  }

  /**
   * @return builder call for retrofit  with return of retofit instance
   */
  Retrofit getRetrofit(OkHttpClient okHttpClient) {
    return new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .callFactory(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
  }
}
