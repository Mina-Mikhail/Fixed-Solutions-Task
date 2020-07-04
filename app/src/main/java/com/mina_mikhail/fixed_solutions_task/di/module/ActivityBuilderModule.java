package com.mina_mikhail.fixed_solutions_task.di.module;

import com.mina_mikhail.fixed_solutions_task.ui.gallery_slider.GallerySliderActivity;
import com.mina_mikhail.fixed_solutions_task.ui.main.MainActivity;
import com.mina_mikhail.fixed_solutions_task.ui.splash.SplashActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(includes = { FragmentBuilderModule.class })
public abstract class ActivityBuilderModule {

  @ContributesAndroidInjector
  abstract SplashActivity bindSplashActivity();

  @ContributesAndroidInjector
  abstract MainActivity bindMainActivity();

  @ContributesAndroidInjector
  abstract GallerySliderActivity bindGallerySliderActivity();
}