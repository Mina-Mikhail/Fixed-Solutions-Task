package com.mina_mikhail.fixed_solutions_task.utils.viewModelFactory;

import android.util.Log;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class ViewModelProviderFactory
    extends ViewModelProvider.NewInstanceFactory {

  private final Map<Class<? extends ViewModel>, Provider<ViewModel>> creators;

  @Inject
  public ViewModelProviderFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators) {
    this.creators = creators;
  }

  @NotNull
  @Override
  public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
    Provider<? extends ViewModel> creator = creators.get(modelClass);
    if (creator == null) {
      for (Map.Entry<Class<? extends ViewModel>, Provider<ViewModel>> entry : creators.entrySet()) {
        if (modelClass.isAssignableFrom(entry.getKey())) {
          creator = entry.getValue();
          break;
        }
      }
    }
    if (creator == null) {
      throw new IllegalArgumentException(
          "unknown ViewModel class , goto dagger.module.ViewModelModule and add method for "
              + modelClass);
    }
    try {
      return (T) creator.get();
    } catch (Exception e) {
      Log.d("VMProviderFactory", e.getMessage());
      throw new RuntimeException(e);
    }
  }
}