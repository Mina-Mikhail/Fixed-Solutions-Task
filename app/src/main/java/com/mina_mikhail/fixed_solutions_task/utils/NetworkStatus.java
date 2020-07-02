package com.mina_mikhail.fixed_solutions_task.utils;

public class NetworkStatus {

  private final int state;
  private final String message;

  public NetworkStatus(int status, String msg) {
    this.state = status;
    this.message = msg;
  }

  public int getState() {
    return state;
  }

  public String getMessage() {
    return message;
  }
}