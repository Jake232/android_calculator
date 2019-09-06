package com.example.calculator_example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {

    // TextView used to display the input and output
    lateinit var txtInput: TextView

    //Map containing state information to ensure illegal operations are not made
    var states = mutableMapOf("lastNumeric" to false, "stateError" to false, "lastDot" to false)

    /**
     *Takes in the current State
     *Returns nothing
     *Sets the screen to be the activity_main.xml
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtInput = findViewById(R.id.txtInput)
    }

    /**
     * Takes in the view (To call from splash_screen.xml it is required)
     * Returns nothing
     * Append the Button.text to the TextView
     */
    fun onDigit(view: View) {
        if (states["stateError"]!!) {
            // If current state is Error, replace the error message
            txtInput.text = (view as Button).text
            states["stateError"] = false
        } else {
            // If not, already there is a valid expression so append to it
            txtInput.append((view as Button).text)
        }
        // Set the flag
        states["lastNumeric"] = true
    }

    /**
     * Takes in the view (To call from splash_screen.xml it is required)
     * Returns nothing
     * Append . to the TextView
     */
    fun onDecimalPoint(view: View) {
        if (states["lastNumeric"]!! && !states["stateError"]!! && !states["lastDot"]!!) {
            txtInput.append(".")
            states["lastNumeric"] = false
            states["lastDot"] = true
        }
    }

    /**
     * Takes in the view (To call from splash_screen.xml it is required)
     * Returns nothing
     * Append +,-,*,/ operators to the TextView
     */
    fun onOperator(view: View) {
        if (states["lastNumeric"]!! && !states["stateError"]!!) {
            txtInput.append((view as Button).text)
            states["lastNumeric"] = false
            states["lastDot"] = false    // Reset the DOT flag
        }
    }


    /**
     * Takes in the view (To call from splash_screen.xml it is required)
     * Returns nothing
     * Clears the TextView on CLR
     */
    fun onClear(view: View) {
        this.txtInput.text = ""
        states["lastNumeric"] = false
        states["stateError"] = false
        states["lastDot"] = false
    }

    /**
     * Takes in the view (To call from splash_screen.xml it is required)
     * Returns nothing
     * Deletes the last character in the Textview
     */
    fun onDel(view: View){
        val copy = txtInput.text.length
        if(copy == 1)
            onClear(view)

        else if(copy >= 1) {
            //Grab the last char from txtInput before its deleted
            val deleted = txtInput.text.takeLast(1)
            val deletedChar = deleted[0]

            //Delete it
            this.txtInput.text = txtInput.text.dropLast(1)

            val operatorz = listOf('+', '-', '*', '/')

            //If the deleted char was an operator allow the user to place another
            if (deletedChar in operatorz)
                states["lastNumeric"] = true

            //If the deleted char was a decimal place allow them to place another
            else if (deletedChar.equals('.')) {
                states["lastDot"] = false
                states["lastNumeric"] = true
            }

            //If it was numeric make sure the char behind it wasn't an operator
            else {
                val checkOp = txtInput.text.takeLast(1)
                val singechar = checkOp[0]
                if (singechar in operatorz)
                    states["lastNumeric"] = false
            }
            states["stateError"] = false
        }
    }

    /**
     * Takes in the view (To call from splash_screen.xml it is required)
     * Returns nothing
     * Calculate the output using Exp4j and display it in the result field
     */
    fun onEqual(view: View) {
        //If any part of ERROR still on screen its still in error state
        if(txtInput.text.contains("E"))
            states["stateError"] = true

        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        if (states["lastNumeric"]!! && !states["stateError"]!!) {
            // Read the expression
            val txt = txtInput.text.toString()
            // Create an Expression (A class from exp4j library)
            val expression = ExpressionBuilder(txt).build()
            try {
                // Calculate the result and display
                val result = expression.evaluate()
                txtInput.text = result.toString()
                states["lastDot"] = true // Result contains a dot
            } catch (ex: ArithmeticException) {
                // Display an error message
                txtInput.text = "Error"
                states["stateError"] = true
                states["lastNumeric"] = false
            }
        }
    }
}
