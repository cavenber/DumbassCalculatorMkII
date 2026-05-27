package com.example.dumbasscalculatormk2

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import com.notkamui.keval.Keval

class ArithmeticOperation : Fragment() {

    lateinit var equation: EditText
    lateinit var answer: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_arithmetic_operation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        equation = view.findViewById<EditText>(R.id.equation)
        answer = view.findViewById<EditText>(R.id.answer)

        // base input
        val btn0 = view.findViewById<Button>(R.id.btn0)
        btn0.setOnClickListener { equation.append("0") }

        val btn1 = view.findViewById<Button>(R.id.btn1)
        btn1.setOnClickListener { equation.append("1") }

        val btn2 = view.findViewById<Button>(R.id.btn2)
        btn2.setOnClickListener { equation.append("2") }

        val btn3 = view.findViewById<Button>(R.id.btn3)
        btn3.setOnClickListener { equation.append("3") }

        val btn4 = view.findViewById<Button>(R.id.btn4)
        btn4.setOnClickListener { equation.append("4") }

        val btn5 = view.findViewById<Button>(R.id.btn5)
        btn5.setOnClickListener { equation.append("5") }

        val btn6 = view.findViewById<Button>(R.id.btn6)
        btn6.setOnClickListener { equation.append("6") }

        val btn7 = view.findViewById<Button>(R.id.btn7)
        btn7.setOnClickListener { equation.append("7") }

        val btn8 = view.findViewById<Button>(R.id.btn8)
        btn8.setOnClickListener { equation.append("8") }

        val btn9 = view.findViewById<Button>(R.id.btn9)
        btn9.setOnClickListener { equation.append("9") }

        val btnDecimalPoint = view.findViewById<Button>(R.id.btnDecimalPoint)
        btnDecimalPoint.setOnClickListener { equation.append(".") }

        val btnPlus = view.findViewById<Button>(R.id.btnPlus)
        btnPlus.setOnClickListener { equation.append(" + ") }

        val btnSubtract = view.findViewById<Button>(R.id.btnSubtract)
        btnSubtract.setOnClickListener { equation.append(" - ") }

        val btnMultiply = view.findViewById<Button>(R.id.btnMultiply)
        btnMultiply.setOnClickListener { equation.append(" * ") }

        val btnDivide = view.findViewById<Button>(R.id.btnDivide)
        btnDivide.setOnClickListener { equation.append(" / ") }

        val btnStartBracket = view.findViewById<Button>(R.id.btnStartBracket)
        btnStartBracket.setOnClickListener { equation.append(" (") }

        val btnEndBracket = view.findViewById<Button>(R.id.btnEndBracket)
        btnEndBracket.setOnClickListener { equation.append(") ") }

        val btnFactorial = view.findViewById<Button>(R.id.btnFactorial)
        btnFactorial.setOnClickListener { equation.append("!") }

        val btnExponent = view.findViewById<Button>(R.id.btnExponent)
        btnExponent.setOnClickListener { equation.append("^") }

        val btnRemainder = view.findViewById<Button>(R.id.btnRemainder)
        btnRemainder.setOnClickListener { equation.append(" % ") }

        val btnExecute = view.findViewById<Button>(R.id.btnExecute)
        btnExecute.setOnClickListener { calculate() }

        val btnReset = view.findViewById<Button>(R.id.btnReset)
        btnReset.setOnClickListener {
            equation.setText("")
            answer.setText("")
        }

        val btnBackspace = view.findViewById<Button>(R.id.btnBackspace)
        btnBackspace.setOnClickListener {
            val length = equation.text.toString().length
            if (length > 0)
                equation.setText(equation.text.delete(length - 1, length))
        }
    }

    fun calculate() {
        try {
            answer.setText(Keval.eval(equation.text.toString()).toString())
        } catch (e: RuntimeException) {answer.setText("Your idiotic actions has resulted in errors.")}
    }
}