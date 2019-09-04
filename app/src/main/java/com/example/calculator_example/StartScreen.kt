package com.example.calculator_example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class StartScreen : AppCompatActivity(){

    /**
    *Takes in the current State
    *Returns nothing
    *Sets the screen to be the activity_splash.xml
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    /**
    * Takes in the view (To call from splash_screen.xml it is required)
    * Returns nothing
    * When Go is pressed it switches to the MainActivity/Calculator
     */
    fun goPressed(v: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        Thread.dumpStack()
    }
}