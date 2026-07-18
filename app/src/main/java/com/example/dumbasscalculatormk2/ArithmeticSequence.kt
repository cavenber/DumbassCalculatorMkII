package com.example.dumbasscalculatormk2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class ArithmeticSequence : Fragment() {

    lateinit var etT1: EditText
    lateinit var etT2: EditText
    lateinit var etN: EditText
    lateinit var etTn: EditText

    lateinit var etEmpty: EditText
    private var selected : EditText? = null // universal selection variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_arithmetic_sequence, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etT1 = view.findViewById<EditText>(R.id.asq_t1)
        etT2 = view.findViewById<EditText>(R.id.asq_t2)
        etN = view.findViewById<EditText>(R.id.asq_n)
        etTn = view.findViewById<EditText>(R.id.asq_tn)

        etT1.showSoftInputOnFocus = false
        etT2.showSoftInputOnFocus = false
        etN.showSoftInputOnFocus = false
        etTn.showSoftInputOnFocus = false

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                selected = v as EditText
            }
        }

        etT1.onFocusChangeListener = listener
        etT2.onFocusChangeListener = listener
        etN.onFocusChangeListener = listener
        etTn.onFocusChangeListener = listener

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

        view?.findViewById<Button>(R.id.btnExecute)
            ?.setOnClickListener {
                if (calculate()){
                    answerLog()
                }
            }

        view?.findViewById<Button>(R.id.btnReset)
            ?.setOnClickListener {
                // input fields
                etT1.setText("")
                etT2.setText("")
                etN.setText("")
                etTn.setText("")
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

        view?.findViewById<Button>(R.id.btnAnswer)
            ?.setOnClickListener { selected?.append(DBHelper(requireContext()).getMostRecentAnswer()) }
    }

    fun calculate() : Boolean {
        try { // write calculations here
            if (etTn.text.toString().isEmpty()) {
                val t1 = Num.evalToNum(etT1.text.toString())
                val t2 = Num.evalToNum(etT2.text.toString())
                val n = Num.evalToNum(etN.text.toString())
                etEmpty = etTn

                val a = t1
                val d = t2 - t1
                val tn = a + (n - 1) * d

                etTn.setText(Num.toString(tn))

            } else if (etN.text.toString().isEmpty()) {
                val t1 = Num.evalToNum(etT1.text.toString())
                val t2 = Num.evalToNum(etT2.text.toString())
                val tn = Num.evalToNum(etTn.text.toString())
                etEmpty = etN

                val a = t1
                val d = t2 - t1
                val n = ((tn - a) / d) + 1

                etN.setText(Num.toString(n))

            } else {
                throw RuntimeException("go to catch block")
            }

            return true

        } catch (e: RuntimeException) {
            etTn.setText(R.string.displeased_message)
            return false
        }
    }

    fun answerLog() {
        if (etEmpty == etTn) {
            DBHelper(requireContext()).saveAnswer(
                "Arithmetic Sequence",
                String.format("T(1) = %s | T(2) = %s | n = %s", etT1.text.toString(), etT2.text.toString(), etN.text.toString()),
                "T(n)",
                etEmpty.text.toString()
            )
        } else if (etEmpty == etN) {
            DBHelper(requireContext()).saveAnswer(
                "Arithmetic Sequence",
                String.format("T(1) = %s | T(2) = %s | T(n) = %s", etT1.text.toString(), etT2.text.toString(), etTn.text.toString()),
                "n",
                etEmpty.text.toString()
            )
        }
    }
}