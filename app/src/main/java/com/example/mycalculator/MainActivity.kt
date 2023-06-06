package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.mycalculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var tvInput: TextView? = null
    var ToggleAC = false
    var lastNumeric : Boolean = false
    val myList = mutableListOf<String>()
    var lastDot : Boolean = false
    var checker : Boolean = true

    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }
    fun onPM(view: View){
        val tvValue = tvInput?.text.toString()
        val calPM = (tvValue.toDouble() *-1).toString()
        tvInput?.text = removeZeroAfterDot(calPM)
    }
    fun onPer(view:View){
        val tvValue = tvInput?.text.toString()
        val calPM = (tvValue.toDouble() /100).toString()
        tvInput?.text = calPM
    }
    fun onClear(view: View){
        tvInput?.text = ""
    }
    fun onDecimalPoint(view:View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
        }
    }
    fun onEqual(view:View){
        myList.add(tvInput!!.text.toString())
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try {
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())}
                else if (tvValue.contains("\u00f7")){
                    val splitValue = tvValue.split("\u00f7")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())}
                else if (tvValue.contains("\u00d7")){
                    val splitValue = tvValue.split("\u00d7")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())}

            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    fun onOperator(view:View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
//                myList[0] = tvInput.toString().dropLast(1)
                myList.add(tvInput!!.text.toString())
                tvInput?.text = ""
            }
        }
    }
    private fun isOperatorAdded(value :String): Boolean{
        return if(value.startsWith("")){
            false
        } else{
            value.contains("\u00f7")|| value.contains("\u00d7") || value.contains("+") || value.contains("-")
        }
    }
    private fun removeZeroAfterDot(result:String):String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length-2)
        return value
    }
}