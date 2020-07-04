package com.mina_mikhail.fixed_solutions_task.di.module;

import com.mina_mikhail.fixed_solutions_task.data.source.remote.ApiInterface;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;

@Module(includes = { RetrofitModule.class })
public class NetworkServiceModule {

  @Singleton
  @Provides
  ApiInterface provideApiInterface(Retrofit retrofit) {
    return retrofit.create(ApiInterface.class);
  }
}