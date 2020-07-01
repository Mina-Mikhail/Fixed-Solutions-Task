package com.mina_mikhail.fixed_solutions_task.data.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.mina_mikhail.fixed_solutions_task.data.enums.NetworkState;

public class RemoteDataSource<T> {

  private final MutableLiveData<Integer> networkState = new MutableLiveData<>();
  private T data;
  private String message;

  public RemoteDataSource() {
    networkState.postValue(NetworkState.DEFAULT);
  }

  public MutableLiveData<Integer> getNetworkState() {
    return networkState;
  }

  public void setDefault() {
    networkState.postValue(NetworkState.DEFAULT);
  }

  public void setIsLoading() {
    networkState.postValue(NetworkState.LOADING);
  }

  public void setIsLoaded(T data) {
    networkState.postValue(NetworkState.LOADED);
    this.data = data;
  }

  public void setIsLoaded(T data, String message) {
    networkState.postValue(NetworkState.LOADED);
    this.data = data;
    this.message = message;
  }

  public void setFailed(@NonNull String errorMessage) {
    this.message = errorMessage;
    networkState.postValue(NetworkState.FAILED);
  }

  public void setNoInternet() {
    networkState.postValue(NetworkState.NO_INTERNET);
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }
}