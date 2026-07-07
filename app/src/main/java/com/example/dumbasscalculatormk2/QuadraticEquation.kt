package com.example.dumbasscalculatormk2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.notkamui.keval.Keval
import kotlin.math.pow

class QuadraticEquation : Fragment() {

    lateinit var etA : EditText
    lateinit var etB : EditText
    lateinit var etC : EditText

    private var selected : EditText? = null // universal selection variable

    lateinit var etX : EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.quadratic_equation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etA = view.findViewById<EditText>(R.id.qe_a)
        etB = view.findViewById<EditText>(R.id.qe_b)
        etC = view.findViewById<EditText>(R.id.qe_c)

        etX = view.findViewById<EditText>(R.id.qe_x)

        etA.showSoftInputOnFocus = false
        etB.showSoftInputOnFocus = false
        etC.showSoftInputOnFocus = false

        // focus change listener
        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                selected = v as EditText
            }
        }

        etA.onFocusChangeListener = listener
        etB.onFocusChangeListener = listener
        etC.onFocusChangeListener = listener

        inputBase(etX)
    }

    fun inputBase(output: EditText) {

        view?.findViewById<Button>(R.id.btn0)
            ?.setOnClickListener { selected?.append("0") }

        view?.findViewById<Button>(R.id.btn1)
            ?.setOnClickListener { selected?.append("1") }

        view?.findViewById<Button>(R.id.btn2)
            ?.setOnClickListener { selected?.append("2") }

        view?.findViewById<Button>(R.id.btn3)
            ?.setOnClickListener { selected?.append("3") }

        view?.findViewById<Button>(R.id.btn4)
            ?.setOnClickListener { selected?.append("4") }

        view?.findViewById<Button>(R.id.btn5)
            ?.setOnClickListener { selected?.append("5") }

        view?.findViewById<Button>(R.id.btn6)
            ?.setOnClickListener { selected?.append("6") }

        view?.findViewById<Button>(R.id.btn7)
            ?.setOnClickListener { selected?.append("7") }

        view?.findViewById<Button>(R.id.btn8)
            ?.setOnClickListener { selected?.append("8") }

        view?.findViewById<Button>(R.id.btn9)
            ?.setOnClickListener { selected?.append("9") }

        view?.findViewById<Button>(R.id.btnDecimalPoint)
            ?.setOnClickListener { selected?.append(".") }

        view?.findViewById<Button>(R.id.btnPlus)
            ?.setOnClickListener { selected?.append("+") }

        view?.findViewById<Button>(R.id.btnSubtract)
            ?.setOnClickListener { selected?.append("-") }

        view?.findViewById<Button>(R.id.btnMultiply)
            ?.setOnClickListener { selected?.append("*") }

        view?.findViewById<Button>(R.id.btnDivide)
            ?.setOnClickListener { selected?.append("/") }

        view?.findViewById<Button>(R.id.btnStartBracket)
            ?.setOnClickListener { selected?.append("(") }

        view?.findViewById<Button>(R.id.btnEndBracket)
            ?.setOnClickListener { selected?.append(")") }

        view?.findViewById<Button>(R.id.btnFactorial)
            ?.setOnClickListener { selected?.append("!") }

        view?.findViewById<Button>(R.id.btnExponent)
            ?.setOnClickListener { selected?.append("^") }

        view?.findViewById<Button>(R.id.btnRemainder)
            ?.setOnClickListener { selected?.append("%") }

        view?.findViewById<Button>(R.id.btnExecute)
            ?.setOnClickListener { calculate() }

        view?.findViewById<Button>(R.id.btnReset)
            ?.setOnClickListener {
                // input fields
                etA.setText("")
                etB.setText("")
                etC.setText("")

                output.setText("")
            }

        view?.findViewById<Button>(R.id.btnBackspace)
            ?.setOnClickListener {
                selected?.let {
                    val length = it.text.length
                    if (length > 0) {
                        it.text.delete(length - 1, length)
                    }
                }
            }
    }


    fun calculate() {
        try { // write calculations here
            val a: Double = Keval.eval(etA.text.toString())
            val b: Double = Keval.eval(etB.text.toString())
            val c: Double = Keval.eval(etC.text.toString())

            val discriminant = b.pow(2) - (4 * a * c)

            if (discriminant < 0) {
                etX.setText("No Real Roots")
            } else if (discriminant == 0.0) {
                etX.setText(((-b + (b.pow(2) - 4 * (a * c)).pow(0.5)) / (2 * a)).toString())
            } else if (discriminant > 0) {
                val x1: Double = (-b + (b.pow(2) - 4 * a * c).pow(0.5)) / (2 * a)
                val x2: Double = (-b - (b.pow(2) - 4 * a * c).pow(0.5)) / (2 * a)

                etX.setText(String.format("%s or %s", x1.toString(), x2.toString()))
            } else {
                etX.setText("You fucking idiot.")
            }
        } catch (e: RuntimeException) {
            etX.setText("You fucking idiot.")
        }
    }
}