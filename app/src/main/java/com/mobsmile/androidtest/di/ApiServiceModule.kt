package com.mobsmile.androidtest.di

import com.mobsmile.androidtest.api.TasksApiService
import dagger.Module
import dagger.Provides

@Module
open class ApiServiceModule {

    @Provides
    open fun provideTaskApiService(): TasksApiService {
        return TasksApiService()
    }
}