package com.mobsmile.androidtest.di

import android.app.Application
import com.mobsmile.androidtest.model.TaskService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class ApiModule {

    @Provides
    @Singleton
    open fun provideTaskService(app: Application): TaskService {
        return TaskService(app)
    }
}