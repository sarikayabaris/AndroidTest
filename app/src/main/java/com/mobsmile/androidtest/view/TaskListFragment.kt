package com.mobsmile.androidtest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobsmile.androidtest.R
import com.mobsmile.androidtest.model.Task
import com.mobsmile.androidtest.model.TaskType
import com.mobsmile.androidtest.viewmodel.TaskListViewModel
import kotlinx.android.synthetic.main.task_list_fragment.*

class TaskListFragment : Fragment() {

    private lateinit var viewModel: TaskListViewModel
    private val taskListAdapter = TaskListAdapter(arrayListOf())

    private val taskListObserver = Observer<List<Task>> { list ->
        list?.let {
            taskListRecyclerView.visibility = View.VISIBLE
            taskListAdapter.updateTasks(it)
        }
    }

    private val loadingErrorObserver = Observer<Boolean> { isError ->
        errorTextView.visibility = if (isError) View.VISIBLE else View.GONE
        if (isError) {
            taskProgressBar.visibility = View.GONE
            taskListRecyclerView.visibility = View.GONE
        }
    }

    private val loadingObserver = Observer<Boolean> { isLoading ->
        taskProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            errorTextView.visibility = View.GONE
            taskListRecyclerView.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.task_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TaskListViewModel::class.java)
        viewModel.tasks.observe(this, taskListObserver)
        viewModel.loadingError.observe(this, loadingErrorObserver)
        viewModel.loading.observe(this, loadingObserver)
        viewModel.refreshTasks()

        taskListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskListAdapter
        }

        swipeRefreshLayout.setOnRefreshListener {
            taskListRecyclerView.visibility = View.GONE
            errorTextView.visibility = View.GONE
            taskProgressBar.visibility = View.VISIBLE
            viewModel.refreshTasks()
            swipeRefreshLayout.isRefreshing = false
        }

        filterGeneralImageView.setOnClickListener { viewModel.filterTasks(TaskType.GENERAL) }
        filterMedicationImageView.setOnClickListener { viewModel.filterTasks(TaskType.MEDICATION) }
        filterHydrationImageView.setOnClickListener { viewModel.filterTasks(TaskType.HYDRATION) }
        filterNutritionImageView.setOnClickListener { viewModel.filterTasks(TaskType.NUTRITION) }
    }
}
