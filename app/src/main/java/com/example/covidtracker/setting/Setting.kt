package com.example.covidtracker.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.covidtracker.R
import com.example.covidtracker.core.PREFERENCE_KEY
import com.example.covidtracker.core.local.shardPreference.SharedPreferenceBuilder
import com.example.covidtracker.main.MainActivity
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.concurrent.TimeUnit

class Setting : AppCompatActivity() {

    val interval = arrayListOf("1 Hours", "2 Hours","5 Hours","Once a day")
    val positions:List<Long> = listOf(1,2,5,24)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val currInterval = SharedPreferenceBuilder(this, PREFERENCE_KEY).getIntervalValue(
            PREFERENCE_KEY)
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, interval
            )
            spinner.adapter = adapter
        }


        val p = positions.indexOf(currInterval)

        spinner.setSelection(p)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
                var interval:Long = 0
                when(position){
                    0 ->{
                        interval = 1
                    }
                    1->{
                        interval = 2
                    }
                    2->{
                        interval = 5
                    }
                    3->{
                        interval = 24
                    }
                }
                SharedPreferenceBuilder(this@Setting, PREFERENCE_KEY).saveIntervalAndUpdateWorkerRequest(
                    PREFERENCE_KEY,interval,TimeUnit.HOURS)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        backBtnId.setOnClickListener{
            finish()
        }
    }
}

