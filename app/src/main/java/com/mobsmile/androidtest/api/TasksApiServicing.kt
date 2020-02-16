package com.mobsmile.androidtest.api

import android.content.Context
import com.mobsmile.androidtest.model.Task

interface TasksApiServicing {
    fun getTasks(context: Context): Array<Task>
}