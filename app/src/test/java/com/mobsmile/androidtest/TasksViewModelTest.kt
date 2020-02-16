package com.mobsmile.androidtest

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.mobsmile.androidtest.api.TasksApiServicing
import com.mobsmile.androidtest.di.AppModule
import com.mobsmile.androidtest.di.DaggerViewModelComponent
import com.mobsmile.androidtest.model.Task
import com.mobsmile.androidtest.model.TaskService
import com.mobsmile.androidtest.model.TaskType
import com.mobsmile.androidtest.viewmodel.TaskListViewModel
import org.hamcrest.Matchers.equalTo
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TasksViewModelTest {

    @Mock
    lateinit var apiService: TaskService

    val app = Mockito.mock(Application::class.java)

    var viewModel = TaskListViewModel(app, true)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .appModule(AppModule(app))
            .apiModule(ApiModuleTest(apiService))
            .build()
            .inject(viewModel)
    }

    @Test
    fun testRefreshTasksWithOnlyOneTaskWillOnlyReturnOneFilteredTask() {
        // Given
        val task = Task(1, "some name", "some description", TaskType.GENERAL)
        val taskList = MutableLiveData<List<Task>>()
        taskList.value = listOf(task)
        Mockito.`when`(apiService.getTasks()).thenReturn(taskList)

        // When
        viewModel.refreshTasks()

        // Then
        Assert.assertThat(1, equalTo(viewModel.tasks.value?.size))
        Assert.assertThat(1, equalTo(viewModel.tasks.value?.get(0)?.id))
        Assert.assertThat("some name", equalTo(viewModel.tasks.value?.get(0)?.name))
        Assert.assertThat("some description", equalTo(viewModel.tasks.value?.get(0)?.description))
    }

    @Test
    fun testFilterTasksByGeneralWillOnlyReturnGeneralTasks() {
        val task1 = Task(1, "some name", "some description", TaskType.GENERAL)
        val task2 = Task(2, "some name", "some description", TaskType.NUTRITION)
        val task3 = Task(3, "some name", "some description", TaskType.GENERAL)
        val taskList = MutableLiveData<List<Task>>()
        taskList.value = listOf(task1, task2, task3)
        Mockito.`when`(apiService.getTasks()).thenReturn(taskList)

        // When
        viewModel.filterTasks(TaskType.GENERAL)

        Assert.assertThat(2, equalTo(viewModel.tasks.value?.size))
    }

    @Test
    fun testFilterTasksByMedicationWillOnlyReturnMedicationTasks() {
        val task1 = Task(1, "some name", "some description", TaskType.MEDICATION)
        val task2 = Task(2, "some name", "some description", TaskType.MEDICATION)
        val task3 = Task(3, "some name", "some description", TaskType.MEDICATION)
        val task4 = Task(4, "some name", "some description", TaskType.GENERAL)
        val taskList = MutableLiveData<List<Task>>()
        taskList.value = listOf(task1, task2, task3, task4)
        Mockito.`when`(apiService.getTasks()).thenReturn(taskList)

        // When
        viewModel.filterTasks(TaskType.MEDICATION)

        Assert.assertThat(3, equalTo(viewModel.tasks.value?.size))
    }

    private class MockTasksService(tasks: Array<Task>) : TasksApiServicing {

        private var tasks: Array<Task>

        init {
            this.tasks = tasks
        }

        override fun getTasks(context: Context): Array<Task> {
            return tasks
        }
    }
}
