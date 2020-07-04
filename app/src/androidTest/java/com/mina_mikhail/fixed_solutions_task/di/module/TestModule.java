package com.mina_mikhail.fixed_solutions_task.di.module;

import dagger.Module;

@Module(includes = { RoomDBModule.class, UtilsModule.class, NetworkServiceModule.class })
public class TestModule {

}