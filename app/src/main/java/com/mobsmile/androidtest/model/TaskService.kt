package com.mobsmile.androidtest.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobsmile.androidtest.api.TasksApiService
import com.mobsmile.androidtest.di.DaggerApiComponent
import javax.inject.Inject

interface TasksServiceInterface {
    fun getTasks(): LiveData<List<Task>>
}

class TaskService(private val context: Context) : TasksServiceInterface {

    @Inject
    lateinit var api: TasksApiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    override fun getTasks(): LiveData<List<Task>> {
        val list = MutableLiveData<List<Task>>()
        list.value = api.getTasks(context).toList()
        return list
    }
}