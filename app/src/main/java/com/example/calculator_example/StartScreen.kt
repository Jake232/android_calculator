package com.example.calculator_example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class StartScreen : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
    fun goPressed(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}