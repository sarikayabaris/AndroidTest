package com.mobsmile.androidtest.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mobsmile.androidtest.di.AppModule
import com.mobsmile.androidtest.di.DaggerViewModelComponent
import com.mobsmile.androidtest.model.Task
import com.mobsmile.androidtest.model.TaskService
import com.mobsmile.androidtest.model.TaskType
import javax.inject.Inject

class TaskListViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: TaskService
    private var injected = false
    val loadingError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }
    var tasks = MutableLiveData<List<Task>>()

    constructor(application: Application, test: Boolean = true) : this(application) {
        injected = test
    }

    fun inject() {
        if (!injected) {
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }

    fun refreshTasks() {
        inject()
        loading.value = true
        tasks.value = apiService.getTasks().value
        loading.value = false
        loadingError.value = false
    }

    fun filterTasks(type: TaskType?) {
        inject()
        loading.value = true
        tasks.value = apiService.getTasks().value?.filter { task -> task.type == type }
        loading.value = false
    }
}
