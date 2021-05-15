package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.yariksoffice.lingver.Lingver
import io.github.kexanie.library.MathView
import kotlin.math.*


class CalculationFragment : Fragment() {
    private val args:CalculationFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val language : String = Lingver.getInstance().getLanguage()
        Lingver.getInstance().setLocale(activity as MainActivity,language)
        val root =  inflater.inflate(R.layout.fragment_calculation, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.fragment_calculate)
        val bottomField : EditText = root.findViewById(R.id.editTextNumberBottom)
        val topField : EditText = root.findViewById(R.id.editTextNumberTop)
        val nameField : MathView = root.findViewById(R.id.mathViewFormulaType)
        val nonBinaryResultField : EditText = root.findViewById(R.id.resultTextView)
        val nonBinaryEqualsField : TextView = root.findViewById(R.id.textViewEquals)
        val aCoefName : MathView = root.findViewById(R.id.mathA)
        val bCoefName : MathView = root.findViewById(R.id.mathB)
        val nCoefName : MathView = root.findViewById(R.id.mathN)
        val aCoefField : EditText = root.findViewById(R.id.editTextNumberA)
        val bCoefField : EditText = root.findViewById(R.id.editTextNumberB)
        val nCoefField : EditText = root.findViewById(R.id.editTextNumberN)
        val binaryResultField : TextView = root.findViewById(R.id.resultBinomialTextView)
        val binaryEqualsField : TextView = root.findViewById(R.id.textViewEqualsBinomial)
        var binaryMode = false

        bottomField.doAfterTextChanged {
            if (it.isNullOrBlank()) {
                modifyText("0", bottomField)
                return@doAfterTextChanged
            }
            val originalText = it.toString()
            try {
                val numberText = originalText.toInt().toString()
                if (originalText != numberText) {
                    modifyText(numberText, bottomField)
                }
            } catch (e: Exception) {
                modifyText("0", bottomField)
            }
        }
        topField.doAfterTextChanged {
            if (it.isNullOrBlank()) {
                modifyText("0", topField)
                return@doAfterTextChanged
            }
            val originalText = it.toString()
            try {
                val numberText = originalText.toInt().toString()
                if (originalText != numberText) {
                    modifyText(numberText, topField)
                }
            } catch (e: Exception) {
                modifyText("0", topField)
            }
        }
        when(args.calcGroup)
        {
            //формула с двумя индексами
            0 -> {
                binaryMode = false
                bottomField.visibility = View.VISIBLE
                topField.visibility = View.VISIBLE
                nameField.visibility = View.VISIBLE
                nonBinaryResultField.visibility = View.VISIBLE
                nonBinaryEqualsField.visibility = View.VISIBLE
                aCoefName.visibility = View.INVISIBLE
                bCoefName.visibility = View.INVISIBLE
                nCoefName.visibility = View.INVISIBLE
                aCoefField.visibility = View.INVISIBLE
                bCoefField.visibility = View.INVISIBLE
                nCoefField.visibility = View.INVISIBLE
                binaryEqualsField.visibility = View.INVISIBLE
                binaryResultField.visibility = View.INVISIBLE
                when (args.formulaType) {
                    "A" -> {
                        nameField.text = MainActivity.calcText[0]
                    }
                    "P" -> {
                        nameField.text = MainActivity.calcText[1]
                        topField.visibility = View.INVISIBLE
                    }
                    "C" -> {
                        nameField.text = MainActivity.calcText[2]
                    }
                    "notA" -> {
                        nameField.text = MainActivity.calcText[3]
                    }
                    "notC" -> {
                        nameField.text = MainActivity.calcText[4]
                    }
                }
            }
            //бином Ньютона
            1 -> {
                binaryMode = true
                bottomField.visibility = View.INVISIBLE
                topField.visibility = View.INVISIBLE
                nameField.visibility = View.INVISIBLE
                nonBinaryResultField.visibility = View.INVISIBLE
                nonBinaryEqualsField.visibility = View.INVISIBLE
                aCoefName.visibility = View.VISIBLE
                bCoefName.visibility = View.VISIBLE
                nCoefName.visibility = View.VISIBLE
                aCoefField.visibility = View.VISIBLE
                bCoefField.visibility = View.VISIBLE
                nCoefField.visibility = View.VISIBLE
                binaryEqualsField.visibility = View.VISIBLE
                binaryResultField.visibility = View.VISIBLE
                aCoefName.text = MainActivity.calcText[5]
                bCoefName.text = MainActivity.calcText[6]
                nCoefName.text = MainActivity.calcText[7]
            }
        }
        val calcBtn : MaterialButton = root.findViewById(R.id.buttonStartCalc)
        var canCompute: Boolean
        calcBtn.setOnClickListener{
            canCompute = true
            if(!binaryMode)
            {
                if (topField.text.toString() == "" && topField.visibility==View.VISIBLE || bottomField.text.toString()=="")
                {
                    canCompute = false
                    val activity: Activity? = activity
                    Toast.makeText(activity, getString(R.string.emptyInputWarning), Toast.LENGTH_LONG).show()
                }
                else
                {
                    if(topField.visibility==View.VISIBLE)
                    {
                        val firstNum = topField.text.toString().toInt()
                        val secondNum = bottomField.text.toString().toInt()
                        if(firstNum > secondNum) {
                            canCompute = false
                            val activity: Activity? = activity
                            Toast.makeText(activity, getString(R.string.bottomCoefficientIncorrect), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            else
            {
                if(aCoefField.text.toString()=="" || bCoefField.text.toString()=="" || nCoefField.text.toString()=="")
                {
                    canCompute = false
                    val activity: Activity? = activity
                    Toast.makeText(activity, getString(R.string.emptyInputWarning), Toast.LENGTH_LONG).show()
                }
            }
            if(canCompute)
            {
                var result = 0.0
                if(!binaryMode)
                {
                    val bottomNum : Int = bottomField.text.toString().toInt()
                    var topNum = 0
                    if(topField.text.toString() != "")
                            topNum = topField.text.toString().toInt()
                    when (args.formulaType) {
                        "A" -> {
                            result = factorial(bottomNum.toLong()) / factorial((bottomNum - topNum).toLong()).toDouble()
                        }
                        "P" -> {
                            result = factorial(bottomNum.toLong()).toDouble()
                        }
                        "C" -> {
                            result = factorial(bottomNum.toLong()) / (factorial(topNum.toLong()) * factorial((bottomNum - topNum).toLong()).toDouble())
                        }
                        "notA" -> {
                            result = bottomNum.toDouble().pow(topNum.toDouble())
                        }
                        "notC" -> {
                            result = factorial((bottomNum + topNum - 1).toLong()) / (factorial(topNum.toLong()) * factorial((bottomNum - 1).toLong()).toDouble())
                        }
                    }
                }
                else {
                    val first : Int = aCoefField.text.toString().toInt()
                    val second : Int = bCoefField.text.toString().toInt()
                    val clcPow : Int = nCoefField.text.toString().toInt()
                    result = (first + second).toDouble()
                    result = result.pow(clcPow)
                }
                if ((result == floor(result)) && !result.isInfinite()) {
                    val roundedResult = floor(result).toLong()
                    nonBinaryResultField.setText(roundedResult.toString())
                    binaryResultField.text=roundedResult.toString()
                }
                else {
                    result = round(result * 10.0) / 10.0
                    nonBinaryResultField.setText(result.toString())
                    binaryResultField.text=result.toString()
                }
            }
        }
        return root
    }

    private fun modifyText(numberText: String, v: EditText) {
        v.setText(numberText)
        v.setSelection(numberText.length)
    }

    private fun factorial(n: Long) :Long {
        var result : Long = 1
        for (d in 1..n) {
            result *= d
        }
        return result
    }

    companion object
}