package com.cavenber.dumbasscalculatormk2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class DirectVariation : Fragment() {

    lateinit var etX: EditText
    lateinit var etK: EditText
    lateinit var etY: EditText

    private var etEmpty: EditText? = null

    private var selected: EditText? = null // universal selection variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_direct_variation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etX = view.findViewById<EditText>(R.id.dv_x)
        etK = view.findViewById<EditText>(R.id.dv_k)
        etY = view.findViewById<EditText>(R.id.dv_y)

        etX.showSoftInputOnFocus = false
        etK.showSoftInputOnFocus = false
        etY.showSoftInputOnFocus = false

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                selected = v as EditText
            }
        }

        etX.onFocusChangeListener = listener
        etK.onFocusChangeListener = listener
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
                etX.setText("")
                etK.setText("")
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
        try { // write calculations here
            if (etK.text.toString().isEmpty()) {
                val x = Num.evalToNum(etX.text.toString())
                val y = Num.evalToNum(etY.text.toString())
                etEmpty = etK

                val k = y / x

                etK.setText(Num.toString(k))

            } else if (etY.text.toString().isEmpty()) {
                val x = Num.evalToNum(etX.text.toString())
                val k = Num.evalToNum(etK.text.toString())
                etEmpty = etY

                val y = x * k

                etY.setText(Num.toString(y))

            } else if (etX.text.toString().isEmpty()) {
                val k = Num.evalToNum(etK.text.toString())
                val y = Num.evalToNum(etY.text.toString())
                etEmpty = etX

                val x = y / k

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
        if (etEmpty == etK) {
            DBHelper(requireContext()).saveAnswer(
                "Direct Variation",
                String.format("x = %s | y = %s", etY.text.toString(), etX.text.toString()),
                "k",
                etK.text.toString()
            )
        } else if (etEmpty == etY) {
            DBHelper(requireContext()).saveAnswer(
                "Direct Variation",
                String.format("x = %s | k = %s", etK.text.toString(), etX.text.toString()),
                "y",
                etY.text.toString()
            )
        } else if (etEmpty == etX) {
            DBHelper(requireContext()).saveAnswer(
                "Direct Variation",
                String.format("k = %s | y = %s", etY.text.toString(), etK.text.toString()),
                "x",
                etX.text.toString()
            )
        }
    }
}