package com.mobsmile.androidtest

import com.mobsmile.androidtest.api.TasksApiService
import com.mobsmile.androidtest.di.ApiServiceModule

class ApiServiceModuleTest(private val mockApi: TasksApiService) : ApiServiceModule() {

    override fun provideTaskApiService(): TasksApiService {
        return mockApi
    }
}