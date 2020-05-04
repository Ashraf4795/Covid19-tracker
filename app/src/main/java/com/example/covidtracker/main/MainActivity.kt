package com.example.covidtracker.main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covidtracker.R
import com.example.covidtracker.global.GlobalViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: GlobalViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_global)
        

    }

}
