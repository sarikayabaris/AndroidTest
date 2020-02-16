package com.mobsmile.androidtest.di

import com.mobsmile.androidtest.viewmodel.TaskListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, AppModule::class, ApiServiceModule::class])
interface ViewModelComponent {
    fun inject(viewModel: TaskListViewModel)
}