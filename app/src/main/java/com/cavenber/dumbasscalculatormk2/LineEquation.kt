package com.cavenber.dumbasscalculatormk2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class LineEquation : Fragment() {
    lateinit var etA: EditText
    lateinit var etB: EditText
    lateinit var etM: EditText
    lateinit var etC: EditText
    lateinit var etX: EditText
    lateinit var etY: EditText

    private var etEmpty: EditText? = null

    private var selected: EditText? = null // universal selection variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_line_equation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etA = view.findViewById<EditText>(R.id.le_a)
        etB = view.findViewById<EditText>(R.id.le_b)
        etM = view.findViewById<EditText>(R.id.le_m)
        etC = view.findViewById<EditText>(R.id.le_c)
        etX = view.findViewById<EditText>(R.id.le_x)
        etY = view.findViewById<EditText>(R.id.le_y)

        etA.showSoftInputOnFocus = false
        etB.showSoftInputOnFocus = false
        etM.showSoftInputOnFocus = false
        etC.showSoftInputOnFocus = false
        etX.showSoftInputOnFocus = false
        etY.showSoftInputOnFocus = false

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                selected = v as EditText
            }
        }

        etA.onFocusChangeListener = listener
        etB.onFocusChangeListener = listener
        etM.onFocusChangeListener = listener
        etC.onFocusChangeListener = listener
        etX.onFocusChangeListener = listener
        etY.onFocusChangeListener = listener

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
                etB.setText("")
                etM.setText("")
                etC.setText("")
                etX.setText("")
                etY.setText("")
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
            if (etM.text.toString().isEmpty() && etC.text.toString().isEmpty()) {
                val a = Num.evalMultiToNum(etA.text.toString())
                val b = Num.evalMultiToNum(etB.text.toString())
                etEmpty = etM

                val m = (b[1] - a[1]) / (b[0] - a[0])
                val c = a[1] - (m * a[0])

                etM.setText(Num.toString(m))
                etC.setText(Num.toString(c))

            } else if (etY.text.toString().isEmpty()) {
                val m = Num.evalToNum(etM.text.toString())
                val c = Num.evalToNum(etC.text.toString())
                val x = Num.evalToNum(etX.text.toString())
                etEmpty = etY

                val y = m * x + c

                etY.setText(Num.toString(y))

            } else if (etX.text.toString().isEmpty()) {
                val m = Num.evalToNum(etM.text.toString())
                val c = Num.evalToNum(etC.text.toString())
                val y = Num.evalToNum(etY.text.toString())
                etEmpty = etX

                val x = (y - c) / m

                etX.setText(Num.toString(x))

            } else {
                throw RuntimeException("to go to catch block")
            }

            return true

        } catch (e: RuntimeException) {
            etY.setText(R.string.displeased_message)
            return false
        }
    }

    fun answerLog() {
        if (etEmpty == etM) {
            DBHelper(requireContext()).saveAnswer(
                "Line Equation",
                String.format("A(%s) | B(%s)", etA.text.toString(), etB.text.toString()),
                "m,c",
                String.format("%s,%s", etM.text.toString(), etC.text.toString())
            )
        } else if (etEmpty == etY) {
            DBHelper(requireContext()).saveAnswer(
                "Line Equation",
                String.format("m = %s | c = %s", etM.text.toString(), etC.text.toString()),
                "y",
                etY.text.toString()
            )
        } else if (etEmpty == etX) {
            DBHelper(requireContext()).saveAnswer(
                "Line Equation",
                String.format("m = %s | c = %s", etM.text.toString(), etC.text.toString()),
                "x",
                etX.text.toString()
            )
        }
    }
}