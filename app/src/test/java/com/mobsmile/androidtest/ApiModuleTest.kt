package com.mobsmile.androidtest

import android.app.Application
import com.mobsmile.androidtest.di.ApiModule
import com.mobsmile.androidtest.model.TaskService

class ApiModuleTest(private val mockApi: TaskService) : ApiModule() {

    override fun provideTaskService(app: Application): TaskService {
        return mockApi
    }
}