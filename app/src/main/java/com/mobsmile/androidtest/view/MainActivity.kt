package com.mobsmile.androidtest.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobsmile.androidtest.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                replace(R.id.fragment_content, TaskListFragment())
                commit()
            }
        }
    }
}
