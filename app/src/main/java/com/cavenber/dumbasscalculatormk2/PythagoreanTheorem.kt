package com.cavenber.dumbasscalculatormk2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import kotlin.math.pow

class PythagoreanTheorem : Fragment() {

    lateinit var etA: EditText
    lateinit var etB: EditText
    lateinit var etC: EditText

    private var etEmpty: EditText? = null
    private var selected: EditText? = null // universal selection variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pythagorean_theorem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etA = view.findViewById<EditText>(R.id.pt_a)
        etB = view.findViewById<EditText>(R.id.pt_b)
        etC = view.findViewById<EditText>(R.id.pt_c)

        etA.showSoftInputOnFocus = false
        etB.showSoftInputOnFocus = false
        etC.showSoftInputOnFocus = false

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                selected = v as EditText
            }
        }

        etA.onFocusChangeListener = listener
        etB.onFocusChangeListener = listener
        etC.onFocusChangeListener = listener

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
                etC.setText("")
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
            if (etC.text.toString().isEmpty()) {
                val a = Num.evalToNum(etA.text.toString())
                val b = Num.evalToNum(etB.text.toString())
                etEmpty = etC

                val c = (a.pow(2) + b.pow(2)).pow(0.5)

                etC.setText(Num.toString(c))

            } else if (etB.text.toString().isEmpty()) {
                val a = Num.evalToNum(etA.text.toString())
                val c = Num.evalToNum(etC.text.toString())
                etEmpty = etB

                val b = (c.pow(2) - a.pow(2)).pow(0.5)

                etB.setText(Num.toString(b))

            } else if (etA.text.toString().isEmpty()) {
                val b = Num.evalToNum(etB.text.toString())
                val c = Num.evalToNum(etC.text.toString())
                etEmpty = etA

                val a = (c.pow(2) - b.pow(2)).pow(0.5)

                etA.setText(Num.toString(a))

            } else {
                throw RuntimeException("to go to catch block")
            }

            return true

        } catch (e: RuntimeException) {
            etC.setText(R.string.displeased_message)
            return false
        }
    }

    fun answerLog() {
        if (etEmpty == etC) {
            DBHelper(requireContext()).saveAnswer(
                "Pythagorean Theorem",
                String.format("a = %s | b = %s", etA.text.toString(), etB.text.toString()),
                "c",
                etC.text.toString()
            )
        } else if (etEmpty == etB) {
            DBHelper(requireContext()).saveAnswer(
                "Pythagorean Theorem",
                String.format("a = %s | c = %s", etA.text.toString(), etC.text.toString()),
                "b",
                etB.text.toString()
            )
        } else if (etEmpty == etA) {
            DBHelper(requireContext()).saveAnswer(
                "Pythagorean Theorem",
                String.format("b = %s | c = %s", etB.text.toString(), etC.text.toString()),
                "a",
                etA.text.toString()
            )
        }
    }
}