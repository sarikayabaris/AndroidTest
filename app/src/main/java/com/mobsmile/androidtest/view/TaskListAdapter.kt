package com.mobsmile.androidtest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobsmile.androidtest.R
import com.mobsmile.androidtest.model.Task
import com.mobsmile.androidtest.model.TaskType
import kotlinx.android.synthetic.main.task_item_view.view.*

class TaskListAdapter(private val taskList: ArrayList<Task>) :
    RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.task_item_view, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount() = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.view.name.text = taskList[position].name
        holder.view.description.text = taskList[position].description
        holder.view.task_type.setImageResource(getTaskImage(taskList[position].type))
    }

    fun updateTasks(newTaskList: List<Task>) {
        taskList.clear()
        taskList.addAll(newTaskList)
        notifyDataSetChanged()
    }

    private fun getTaskImage(taskType: TaskType): Int {
        return when (taskType) {
            TaskType.GENERAL -> R.drawable.general
            TaskType.HYDRATION -> R.drawable.hydration
            TaskType.MEDICATION -> R.drawable.medication
            TaskType.NUTRITION -> R.drawable.nutrition
        }
    }

    class TaskViewHolder(var view: View) : RecyclerView.ViewHolder(view)
}