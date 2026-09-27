package com.cavenber.dumbasscalculatormk2.dependencies

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.cavenber.dumbasscalculatormk2.R

class InputSpecial(val view: View) {

    var selected: EditText? = null

    init {
        view.findViewById<Button>(R.id.btnSqrt)
            ?.setOnClickListener { selected?.append("sqrt(") }

        view.findViewById<Button>(R.id.btnCbrt)
            ?.setOnClickListener { selected?.append("cbrt(") }

        view.findViewById<Button>(R.id.btnNthrt)
            ?.setOnClickListener { selected?.append("nthrt(") }

        view.findViewById<Button>(R.id.btnSin)
            ?.setOnClickListener { selected?.append("sin(") }

        view.findViewById<Button>(R.id.btnAsin)
            ?.setOnClickListener { selected?.append("asin(") }

        view.findViewById<Button>(R.id.btnCos)
            ?.setOnClickListener { selected?.append("cos(") }

        view.findViewById<Button>(R.id.btnAcos)
            ?.setOnClickListener { selected?.append("acos(") }

        view.findViewById<Button>(R.id.btnTan)
            ?.setOnClickListener { selected?.append("tan(") }

        view.findViewById<Button>(R.id.btnAtan)
            ?.setOnClickListener { selected?.append("atan(") }

        view.findViewById<Button>(R.id.btnLn)
            ?.setOnClickListener { selected?.append("ln(") }

        view.findViewById<Button>(R.id.btnLog10)
            ?.setOnClickListener { selected?.append("log10(") }

        view.findViewById<Button>(R.id.btnLog2)
            ?.setOnClickListener { selected?.append("log2(") }

        view.findViewById<Button>(R.id.btnE)
            ?.setOnClickListener { selected?.append("e") }

        view.findViewById<Button>(R.id.btnPi)
            ?.setOnClickListener { selected?.append("PI") }

        view.findViewById<Button>(R.id.btnRound)
            ?.setOnClickListener { selected?.append("round(") }
    }
}