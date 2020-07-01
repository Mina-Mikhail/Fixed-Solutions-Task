package com.mina_mikhail.fixed_solutions_task.data.source.remote.response;

public class BaseResponse {

  /**
   * page : 1
   * total_results : 10000
   * total_pages : 500
   */

  private int page;
  private int total_results;
  private int total_pages;

  public int getPage() {
    return page;
  }

  public int getTotal_results() {
    return total_results;
  }

  public int getTotal_pages() {
    return total_pages;
  }
}
