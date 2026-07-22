package com.cavenber.dumbasscalculatormk2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ToggleButton
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow

class CosineFormula : Fragment() {

    lateinit var eta: EditText
    lateinit var etb: EditText
    lateinit var etC: EditText
    lateinit var etc: EditText

    lateinit var tgDegree: ToggleButton

    private var etEmpty: EditText? = null
    private var selected: EditText? = null // universal selection variable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cosine_formula, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eta = view.findViewById<EditText>(R.id.cf_a)
        etb = view.findViewById<EditText>(R.id.cf_b)
        etC = view.findViewById<EditText>(R.id.cf_C)
        etc = view.findViewById<EditText>(R.id.cf_c)

        tgDegree = view.findViewById<ToggleButton>(R.id.cf_tg_degree)

        eta.showSoftInputOnFocus = false
        etb.showSoftInputOnFocus = false
        etC.showSoftInputOnFocus = false
        etc.showSoftInputOnFocus = false

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                selected = v as EditText
            }
        }

        eta.onFocusChangeListener = listener
        etb.onFocusChangeListener = listener
        etC.onFocusChangeListener = listener
        etc.onFocusChangeListener = listener

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
                eta.setText("")
                etb.setText("")
                etC.setText("")
                etc.setText("")
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
            if (etc.text.toString().isEmpty()) {
                val a = Num.evalToNum(eta.text.toString())
                val b = Num.evalToNum(etb.text.toString())
                var C = Num.evalToNum(etC.text.toString())
                etEmpty = etc

                if (tgDegree.isChecked) {
                    C = Math.toRadians(C)
                }

                val c = (a.pow(2) + b.pow(2) - (2 * a * b * cos(C))).pow(0.5)

                etc.setText(Num.toString(c))

            } else if (etC.text.toString().isEmpty()) {
                val a = Num.evalToNum(eta.text.toString())
                val b = Num.evalToNum(etb.text.toString())
                val c = Num.evalToNum(etc.text.toString())
                etEmpty = etC

                var C = acos((a.pow(2) + b.pow(2) - c.pow(2)) / (2 * a * b))

                if (tgDegree.isChecked) {
                    C = Math.toDegrees(C)
                }

                etC.setText(Num.toString(C))

            } else {
                throw RuntimeException("to go to catch block")
            }

            return true

        } catch (e: RuntimeException) {
            etc.setText(R.string.displeased_message)
            return false
        }
    }

    fun answerLog() {
        if (etEmpty == etc) {
            DBHelper(requireContext()).saveAnswer(
                "Cosine Formula",
                String.format("a = %s | b = %s | C = %s", eta.text.toString(), etb.text.toString(), etC.text.toString()),
                "c",
                etc.text.toString()
            )
        } else if (etEmpty == etC) {
            DBHelper(requireContext()).saveAnswer(
                "Cosine Formula",
                String.format("a = %s | b = %s | c = %s", eta.text.toString(), etb.text.toString(), etc.text.toString()),
                "C",
                etC.text.toString()
            )
        }
    }
}