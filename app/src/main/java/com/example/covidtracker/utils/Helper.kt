package com.example.covidtracker.utils

import java.lang.StringBuilder

class Helper {


    companion object {
        fun convertNumber(number:Long) :String {
            val stringBuilder = StringBuilder()
            when(number) {
                in 1000..999999 ->{
                    stringBuilder.append(String.format("%.1f",(number/1000f)))
                    stringBuilder.append("K")
                    return stringBuilder.toString()
                }
                in 1000000..999999999 ->{
                    stringBuilder.append(String.format("%.1f",(number/1000000f)))
                    stringBuilder.append("M")
                    return stringBuilder.toString()
                }
            }
            return number.toString()
        }
    }
}