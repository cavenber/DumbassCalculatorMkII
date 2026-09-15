package com.cavenber.dumbasscalculatormk2

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.EditText

class InputBase(val view: View, val context: Context, val clearET: () -> Unit, val execution: () -> Unit, val answerHelper: () -> Unit) {

    var selected: EditText? = null
    var etEmpty: EditText? = null

    init {
        view.findViewById<Button>(R.id.btn0)
            ?.setOnClickListener { selected?.append("0") }

        view.findViewById<Button>(R.id.btn1)
            ?.setOnClickListener { selected?.append("1") }

        view.findViewById<Button>(R.id.btn2)
            ?.setOnClickListener { selected?.append("2") }

        view.findViewById<Button>(R.id.btn3)
            ?.setOnClickListener { selected?.append("3") }

        view.findViewById<Button>(R.id.btn4)
            ?.setOnClickListener { selected?.append("4") }

        view.findViewById<Button>(R.id.btn5)
            ?.setOnClickListener { selected?.append("5") }

        view.findViewById<Button>(R.id.btn6)
            ?.setOnClickListener { selected?.append("6") }

        view.findViewById<Button>(R.id.btn7)
            ?.setOnClickListener { selected?.append("7") }

        view.findViewById<Button>(R.id.btn8)
            ?.setOnClickListener { selected?.append("8") }

        view.findViewById<Button>(R.id.btn9)
            ?.setOnClickListener { selected?.append("9") }

        view.findViewById<Button>(R.id.btnDecimalPoint)
            ?.setOnClickListener { selected?.append(".") }

        view.findViewById<Button>(R.id.btnPlus)
            ?.setOnClickListener { selected?.append("+") }

        view.findViewById<Button>(R.id.btnSubtract)
            ?.setOnClickListener { selected?.append("-") }

        view.findViewById<Button>(R.id.btnMultiply)
            ?.setOnClickListener { selected?.append("*") }

        view.findViewById<Button>(R.id.btnDivide)
            ?.setOnClickListener { selected?.append("/") }

        view.findViewById<Button>(R.id.btnStartBracket)
            ?.setOnClickListener { selected?.append("(") }

        view.findViewById<Button>(R.id.btnEndBracket)
            ?.setOnClickListener { selected?.append(")") }

        view.findViewById<Button>(R.id.btnFactorial)
            ?.setOnClickListener { selected?.append("!") }

        view.findViewById<Button>(R.id.btnExponent)
            ?.setOnClickListener { selected?.append("^") }

        view.findViewById<Button>(R.id.btnRemainder)
            ?.setOnClickListener { selected?.append("%") }

        view.findViewById<Button>(R.id.btnComma)
            ?.setOnClickListener { selected?.append(",") }

        view.findViewById<Button>(R.id.btnExecute)
            ?.setOnClickListener {
                if (execute()){
                    answerLog()
                }
            }

        view.findViewById<Button>(R.id.btnBackspace)
            ?.setOnLongClickListener {
                clearET()
                true
            }

        view.findViewById<Button>(R.id.btnBackspace)
            ?.setOnClickListener {
                selected?.let {
                    val length = it.text.length
                    if (length > 0) {
                        it.text.delete(length - 1, length)
                    }
                }
                etEmpty?.setText("")
            }

        view.findViewById<Button>(R.id.btnAnswer)
            ?.setOnClickListener { selected?.append(DBHelper(context).getMostRecentAnswer()) }
    }

    fun execute() : Boolean {
        try {
            execution()
            return true
        } catch (e: DataIncompleteException) {
            return false
        } catch (e: RuntimeException) {
            etEmpty?.setText(R.string.displeased_message)
            return false
        }
    }

    fun answerLog() {
        answerHelper()
    }
}