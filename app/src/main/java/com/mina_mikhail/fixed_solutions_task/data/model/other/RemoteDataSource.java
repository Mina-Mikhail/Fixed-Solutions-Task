package com.mina_mikhail.fixed_solutions_task.data.model.other;

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

  public void setIsLoadedFromRemote(T data) {
    networkState.postValue(NetworkState.LOADED_FROM_REMOTE);
    this.data = data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public void setIsLoadedFromRemote(T data, String message) {
    networkState.postValue(NetworkState.LOADED_FROM_REMOTE);
    this.data = data;
    this.message = message;
  }

  public void setIsLoadedFromLocal(T data, String message) {
    networkState.postValue(NetworkState.LOADED_FROM_LOCAL);
    this.data = data;
    this.message = message;
  }

  public void setFailed(@NonNull String errorMessage) {
    this.message = errorMessage;
    networkState.postValue(NetworkState.FAILED);
  }

  public void setNetworkState(int state) {
    networkState.postValue(state);
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