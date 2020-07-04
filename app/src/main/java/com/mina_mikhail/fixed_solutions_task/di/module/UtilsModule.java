package com.mina_mikhail.fixed_solutions_task.di.module;

import android.content.Context;
import androidx.paging.PagedList;
import com.mina_mikhail.fixed_solutions_task.utils.NetworkUtils;
import com.mina_mikhail.fixed_solutions_task.utils.ResourceProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class UtilsModule {

  public static final int PAGE_SIZE = 20;
  public static final int PAGE_PRE_FETCH_DISTANCE = 4;

  /**
   * @return ResourceProvider which provides resources like strings , etc...
   */
  @Provides
  @Singleton
  ResourceProvider provideResourceProvider(Context context) {
    return new ResourceProvider(context);
  }

  @Provides
  @Singleton
  NetworkUtils provideNetworkUtils(Context context) {
    return new NetworkUtils(context);
  }

  @Provides
  @Singleton
  PagedList.Config providePagedListConfig() {
    return (new PagedList.Config.Builder())
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE)
        .setPrefetchDistance(PAGE_PRE_FETCH_DISTANCE)
        .build();
  }
}