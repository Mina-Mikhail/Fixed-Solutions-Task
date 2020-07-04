package com.mina_mikhail.fixed_solutions_task.di.module;

import dagger.Module;

@Module(includes = {
    ActivityBuilderModule.class, ViewModelModule.class,
    NetworkServiceModule.class, RoomDBModule.class, UtilsModule.class
})
public class AppModule {

}