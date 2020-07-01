package com.mina_mikhail.fixed_solutions_task.data.source.remote;

import com.mina_mikhail.fixed_solutions_task.data.model.MovieDetails;
import com.mina_mikhail.fixed_solutions_task.data.source.remote.response.PopularMoviesResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

  @GET("discover/movie")
  Single<PopularMoviesResponse> getMovies(@Query("sort_by") String sortBy);

  @GET("movie/{movieID}")
  Single<MovieDetails> getMovieDetails(@Path("movieID") int movieID);
}