package com.mobsmile.androidtest.api

import android.content.Context
import com.google.gson.Gson
import com.mobsmile.androidtest.model.Task

class TasksApiService : TasksApiServicing {

    override fun getTasks(context: Context): Array<Task> {
        val assetManager = context.assets
        val inputStream = assetManager.open("tasks.json")
        val tasks = Gson().fromJson(inputStream.bufferedReader(), Array<Task>::class.java)
        Thread.sleep(2000) // two second delay to simulate a slow network connection
        return tasks
    }
}