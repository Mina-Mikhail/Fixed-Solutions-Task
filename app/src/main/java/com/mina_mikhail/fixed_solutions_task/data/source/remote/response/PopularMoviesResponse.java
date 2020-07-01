package com.mina_mikhail.fixed_solutions_task.data.source.remote.response;

import com.mina_mikhail.fixed_solutions_task.data.model.api.Movie;
import java.util.List;

public class PopularMoviesResponse
    extends BaseResponse {

  private List<Movie> results;

  public List<Movie> getResults() {
    return results;
  }
}
