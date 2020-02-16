package com.mobsmile.androidtest.di

import com.mobsmile.androidtest.model.TaskService
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, ApiServiceModule::class])
interface ApiComponent {
    fun inject(service: TaskService)
}