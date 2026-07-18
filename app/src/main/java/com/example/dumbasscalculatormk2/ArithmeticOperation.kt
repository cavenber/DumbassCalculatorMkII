package com.example.dumbasscalculatormk2

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup

class ArithmeticOperation : Fragment() {

    lateinit var equation: EditText
    lateinit var answer: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_arithmetic_operation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        equation = view.findViewById<EditText>(R.id.equation)
        answer = view.findViewById<EditText>(R.id.answer)

        inputBase(equation, answer)
        inputSpecial(equation)

    }

    fun inputBase(input: EditText, output: EditText) {

        view?.findViewById<Button>(R.id.btn0)
            ?.setOnClickListener { input.append("0") }

        view?.findViewById<Button>(R.id.btn1)
            ?.setOnClickListener { input.append("1") }

        view?.findViewById<Button>(R.id.btn2)
            ?.setOnClickListener { input.append("2") }

        view?.findViewById<Button>(R.id.btn3)
            ?.setOnClickListener { input.append("3") }

        view?.findViewById<Button>(R.id.btn4)
            ?.setOnClickListener { input.append("4") }

        view?.findViewById<Button>(R.id.btn5)
            ?.setOnClickListener { input.append("5") }

        view?.findViewById<Button>(R.id.btn6)
            ?.setOnClickListener { input.append("6") }

        view?.findViewById<Button>(R.id.btn7)
            ?.setOnClickListener { input.append("7") }

        view?.findViewById<Button>(R.id.btn8)
            ?.setOnClickListener { input.append("8") }

        view?.findViewById<Button>(R.id.btn9)
            ?.setOnClickListener { input.append("9") }

        view?.findViewById<Button>(R.id.btnDecimalPoint)
            ?.setOnClickListener { input.append(".") }

        view?.findViewById<Button>(R.id.btnPlus)
            ?.setOnClickListener { input.append("+") }

        view?.findViewById<Button>(R.id.btnSubtract)
            ?.setOnClickListener { input.append("-") }

        view?.findViewById<Button>(R.id.btnMultiply)
            ?.setOnClickListener { input.append("*") }

        view?.findViewById<Button>(R.id.btnDivide)
            ?.setOnClickListener { input.append("/") }

        view?.findViewById<Button>(R.id.btnStartBracket)
            ?.setOnClickListener { input.append("(") }

        view?.findViewById<Button>(R.id.btnEndBracket)
            ?.setOnClickListener { input.append(")") }

        view?.findViewById<Button>(R.id.btnFactorial)
            ?.setOnClickListener { input.append("!") }

        view?.findViewById<Button>(R.id.btnExponent)
            ?.setOnClickListener { input.append("^") }

        view?.findViewById<Button>(R.id.btnRemainder)
            ?.setOnClickListener { input.append("%") }

        view?.findViewById<Button>(R.id.btnExecute)
            ?.setOnClickListener {
                if (calculate()) {
                    answerLog()
                }
            }

        view?.findViewById<Button>(R.id.btnReset)
            ?.setOnClickListener {
                input.setText("")
                output.setText("")
            }

        view?.findViewById<Button>(R.id.btnBackspace)
            ?.setOnClickListener {
                val length = input.text?.length ?: 0
                if (length > 0) {
                    input.setText(input.text?.delete(length - 1, length))
                }
            }

        view?.findViewById<Button>(R.id.btnAnswer)
            ?.setOnClickListener { input.append(DBHelper(requireContext()).getMostRecentAnswer()) }
    }

    fun inputSpecial(input: EditText) {

        view?.findViewById<Button>(R.id.btnSqrt)
            ?.setOnClickListener { input.append("sqrt(") }

        view?.findViewById<Button>(R.id.btnCbrt)
            ?.setOnClickListener { input.append("cbrt(") }

        view?.findViewById<Button>(R.id.btnNthrt)
            ?.setOnClickListener { input.append("nthrt(") }

        view?.findViewById<Button>(R.id.btnSin)
            ?.setOnClickListener { input.append("sin(") }

        view?.findViewById<Button>(R.id.btnAsin)
            ?.setOnClickListener { input.append("asin(") }

        view?.findViewById<Button>(R.id.btnCos)
            ?.setOnClickListener { input.append("cos(") }

        view?.findViewById<Button>(R.id.btnAcos)
            ?.setOnClickListener { input.append("acos(") }

        view?.findViewById<Button>(R.id.btnTan)
            ?.setOnClickListener { input.append("tan(") }

        view?.findViewById<Button>(R.id.btnAtan)
            ?.setOnClickListener { input.append("atan(") }

        view?.findViewById<Button>(R.id.btnLn)
            ?.setOnClickListener { input.append("ln(") }

        view?.findViewById<Button>(R.id.btnLog10)
            ?.setOnClickListener { input.append("log10(") }

        view?.findViewById<Button>(R.id.btnLog2)
            ?.setOnClickListener { input.append("log2(") }

        view?.findViewById<Button>(R.id.btnE)
            ?.setOnClickListener { input.append("e") }

        view?.findViewById<Button>(R.id.btnPi)
            ?.setOnClickListener { input.append("PI") }

        view?.findViewById<Button>(R.id.btnComma)
            ?.setOnClickListener { input.append(",") }
    }

    fun calculate() : Boolean {
        try {
            answer.setText(Num.toString(Num.evalToNum(equation.text.toString())))
            return true
        } catch (e: RuntimeException) {
            answer.setText(R.string.displeased_message)
            return false
        }
    }

    fun answerLog() {
        DBHelper(requireContext()).saveAnswer(
            "Arithmetic Operation",
            equation.text.toString(),
            "",
            answer.text.toString()
        )
    }

}