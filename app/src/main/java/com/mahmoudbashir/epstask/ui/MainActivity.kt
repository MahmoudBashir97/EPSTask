package com.mahmoudbashir.epstask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.mahmoudbashir.epstask.R
import com.mahmoudbashir.epstask.repository.StRepository
import com.mahmoudbashir.epstask.room.StDatabase
import com.mahmoudbashir.epstask.viewModel.StViewModel
import com.mahmoudbashir.epstask.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: StViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val repo = StRepository(StDatabase.invoke(this))
        val viewModelFactory = ViewModelFactory(application,repo)
        viewModel = ViewModelProvider(this,viewModelFactory).get(StViewModel::class.java)
    }
}