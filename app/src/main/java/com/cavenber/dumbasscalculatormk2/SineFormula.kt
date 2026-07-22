package com.cavenber.dumbasscalculatormk2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import kotlin.math.asin
import kotlin.math.sin

class SineFormula : Fragment() {

    lateinit var etA: EditText
    lateinit var eta: EditText
    lateinit var etB: EditText
    lateinit var etb: EditText

    lateinit var tgDegree: ToggleButton

    private var etEmpty: EditText? = null
    private var selected : EditText? = null // universal selection variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sine_formula, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etA = view.findViewById<EditText>(R.id.sf_A)
        eta = view.findViewById<EditText>(R.id.sf_a)
        etB = view.findViewById<EditText>(R.id.sf_B)
        etb = view.findViewById<EditText>(R.id.sf_b)

        tgDegree = view.findViewById<ToggleButton>(R.id.sf_tg_degree)

        etA.showSoftInputOnFocus = false
        eta.showSoftInputOnFocus = false
        etB.showSoftInputOnFocus = false
        etb.showSoftInputOnFocus = false

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                selected = v as EditText
            }
        }

        etA.onFocusChangeListener = listener
        eta.onFocusChangeListener = listener
        etB.onFocusChangeListener = listener
        etb.onFocusChangeListener = listener

        inputBase()
    }

    fun inputBase() {

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

        view?.findViewById<Button>(R.id.btnComma)
            ?.setOnClickListener { selected?.append(",") }

        view?.findViewById<Button>(R.id.btnExecute)
            ?.setOnClickListener {
                if (calculate()){
                    answerLog()
                }
            }

        view?.findViewById<Button>(R.id.btnBackspace)
            ?.setOnLongClickListener {
                // input fields
                etA.setText("")
                eta.setText("")
                etB.setText("")
                etb.setText("")
                true
            }

        view?.findViewById<Button>(R.id.btnBackspace)
            ?.setOnClickListener {
                selected?.let {
                    val length = it.text.length
                    if (length > 0) {
                        it.text.delete(length - 1, length)
                    }
                }
                etEmpty?.setText("")
                etEmpty = null
            }

        view?.findViewById<Button>(R.id.btnAnswer)
            ?.setOnClickListener { selected?.append(DBHelper(requireContext()).getMostRecentAnswer()) }
    }

    fun calculate() : Boolean {
        try {
            if (etb.text.toString().isEmpty()) {
                var A = Num.evalToNum(etA.text.toString())
                val a = Num.evalToNum(eta.text.toString())
                var B = Num.evalToNum(etB.text.toString())
                etEmpty = etb

                if (tgDegree.isChecked) {
                    A = Math.toRadians(A)
                    B = Math.toRadians(B)
                }

                val b = (a * sin(B)) / sin(A)

                etb.setText(Num.toString(b))

            } else if (etB.text.toString().isEmpty()) {
                var A = Num.evalToNum(etA.text.toString())
                val a = Num.evalToNum(eta.text.toString())
                val b = Num.evalToNum(etb.text.toString())
                etEmpty = etB

                if (tgDegree.isChecked) {
                    A = Math.toRadians(A)
                }

                var B = asin((sin(A) * b) / a)

                if (tgDegree.isChecked) {
                    B = Math.toDegrees(B)
                }

                etB.setText(Num.toString(B))

            } else {
                throw RuntimeException("to go to catch block")
            }

            return true

        } catch (e: RuntimeException) {
            etb.setText(R.string.displeased_message)
            return false
        }
    }

    fun answerLog() {
        if (etEmpty == etb) {
            DBHelper(requireContext()).saveAnswer(
                "Sine Formula",
                String.format("A = %s | a = %s | B = %s", etA.text.toString(), eta.text.toString(), etB.text.toString()),
                "b",
                etb.text.toString()
            )
        } else if (etEmpty == etB) {
            DBHelper(requireContext()).saveAnswer(
                "Sine Formula",
                String.format("A = %s | a = %s | b = %s", etA.text.toString(), eta.text.toString(), etb.text.toString()),
                "B",
                etB.text.toString()
            )
        }

    }
}