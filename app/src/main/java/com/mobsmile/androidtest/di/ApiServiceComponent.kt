package com.mobsmile.androidtest.di

import com.mobsmile.androidtest.api.TasksApiService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiServiceModule::class])
interface ApiServiceComponent {
    fun inject(service: TasksApiService)
}