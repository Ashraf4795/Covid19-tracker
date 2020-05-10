package com.example.covidtracker.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.covidtracker.R
import com.example.covidtracker.core.Refreshable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    //todo: setup workmanager
    //todo: save to database
    //todo:check connectivity, if offline load from db if exist
    //todo:setup recyclerview
    //todo:setup statistics chart


    private lateinit var navController: NavController

    lateinit var currentFragment: Refreshable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Getting the Navigation Controller
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        //Setting the navigation controller to Bottom Nav
        navigationViewId.setupWithNavController(navController)


    }


    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }


}
