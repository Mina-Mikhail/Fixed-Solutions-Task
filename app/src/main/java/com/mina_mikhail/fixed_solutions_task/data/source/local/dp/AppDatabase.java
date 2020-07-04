package com.mina_mikhail.fixed_solutions_task.data.source.local.dp;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import com.mina_mikhail.fixed_solutions_task.data.model.api.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.MovieDetailsDao;
import com.mina_mikhail.fixed_solutions_task.data.source.local.dp.dao.PopularMoviesDao;
import com.mina_mikhail.fixed_solutions_task.utils.Converters;

@Database(entities = { Movie.class, MovieDetails.class }, version = 1, exportSchema = false)
@TypeConverters({ Converters.class })
public abstract class AppDatabase
    extends RoomDatabase {

  public abstract PopularMoviesDao getPopularMoviesDao();

  public abstract MovieDetailsDao getMovieDetailsDao();
}