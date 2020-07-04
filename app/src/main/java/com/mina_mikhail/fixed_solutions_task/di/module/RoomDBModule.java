package com.mina_mikhail.fixed_solutions_task.di.module;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Room;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.AppDatabase;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.MovieDetailsDao;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.PopularMoviesDao;
import com.mina_mikhail.fixed_solutions_task.utils.Constants;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class RoomDBModule {

  @Provides
  @Singleton
  AppDatabase provideDatabase(Context context) {
    return Room.databaseBuilder(context
        , AppDatabase.class
        , Constants.DB_NAME)
        .build();
  }

  @Provides
  @Singleton
  PopularMoviesDao providePopularMoviesDao(@NonNull AppDatabase appDatabase) {
    return appDatabase.getPopularMoviesDao();
  }

  @Provides
  @Singleton
  MovieDetailsDao provideMovieDetailsDao(@NonNull AppDatabase appDatabase) {
    return appDatabase.getMovieDetailsDao();
  }
}