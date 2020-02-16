package com.mobsmile.androidtest

import android.app.Application
import com.mobsmile.androidtest.api.TasksApiService
import com.mobsmile.androidtest.di.DaggerApiComponent
import com.mobsmile.androidtest.model.Task
import com.mobsmile.androidtest.model.TaskService
import com.mobsmile.androidtest.model.TaskType
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TaskServiceTest {

    @Mock
    lateinit var taskApiService: TasksApiService

    val app = Mockito.mock(Application::class.java)

    var taskService = TaskService(app)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerApiComponent.builder()
            .apiServiceModule(ApiServiceModuleTest(taskApiService))
            .build()
            .inject(taskService)
    }

    @Test
    fun testGetTasksFromApiServicesReturnsTasksList() {
        // Given
        val task = Task(1, "some name", "some description", TaskType.GENERAL)
        val task2 = Task(2, "some name", "some description", TaskType.MEDICATION)
        Mockito.`when`(taskApiService.getTasks(app)).thenReturn(arrayOf(task, task2))

        // Then
        Assert.assertThat(2, Matchers.equalTo(taskService.getTasks().value?.size))
    }
}