package com.example.calculator_example

import android.content.Context
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent



fun go_pressed(){
    val intent = Intent(this, NextActivity::class.java)
// To pass any data to next activity
    intent.putExtra("keyIdentifier", value)
// start your next activity
    startActivity(intent)
}