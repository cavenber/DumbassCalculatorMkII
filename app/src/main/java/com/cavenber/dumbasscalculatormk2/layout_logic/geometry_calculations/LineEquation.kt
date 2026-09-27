package com.cavenber.dumbasscalculatormk2.layout_logic.geometry_calculations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.cavenber.dumbasscalculatormk2.R
import com.cavenber.dumbasscalculatormk2.dependencies.DBHelper
import com.cavenber.dumbasscalculatormk2.dependencies.InputBase
import com.cavenber.dumbasscalculatormk2.dependencies.Num

class LineEquation : Fragment() {
    lateinit var etA: EditText
    lateinit var etB: EditText
    lateinit var etM: EditText
    lateinit var etC: EditText
    lateinit var etX: EditText
    lateinit var etY: EditText

    lateinit var inputBase: InputBase

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

        inputBase = InputBase(
            view, requireContext(),
            {
                etA.setText("")
                etB.setText("")
                etM.setText("")
                etC.setText("")
                etX.setText("")
                etY.setText("")
            },
            {
                if (etM.text.toString().isEmpty() && etC.text.toString().isEmpty()) {
                    val a = Num.evalMultiToNum(etA.text.toString())
                    val b = Num.evalMultiToNum(etB.text.toString())
                    inputBase.etEmpty = etM

                    val m = (b[1] - a[1]) / (b[0] - a[0])
                    val c = a[1] - (m * a[0])

                    etM.setText(Num.toString(m))
                    etC.setText(Num.toString(c))

                } else if (etY.text.toString().isEmpty()) {
                    val m = Num.evalToNum(etM.text.toString())
                    val c = Num.evalToNum(etC.text.toString())
                    val x = Num.evalToNum(etX.text.toString())
                    inputBase.etEmpty = etY

                    val y = m * x + c

                    etY.setText(Num.toString(y))

                } else if (etX.text.toString().isEmpty()) {
                    val m = Num.evalToNum(etM.text.toString())
                    val c = Num.evalToNum(etC.text.toString())
                    val y = Num.evalToNum(etY.text.toString())
                    inputBase.etEmpty = etX

                    val x = (y - c) / m

                    etX.setText(Num.toString(x))

                } else {
                    throw RuntimeException("to go to catch block")
                }
            },
            {
                if (inputBase.etEmpty == etM) {
                    DBHelper(requireContext()).saveAnswer(
                        "Line Equation",
                        String.format("A(%s) | B(%s)", etA.text.toString(), etB.text.toString()),
                        "m,c",
                        String.format("%s,%s", etM.text.toString(), etC.text.toString())
                    )
                } else if (inputBase.etEmpty == etY) {
                    DBHelper(requireContext()).saveAnswer(
                        "Line Equation",
                        String.format("m = %s | c = %s", etM.text.toString(), etC.text.toString()),
                        "y",
                        etY.text.toString()
                    )
                } else if (inputBase.etEmpty == etX) {
                    DBHelper(requireContext()).saveAnswer(
                        "Line Equation",
                        String.format("m = %s | c = %s", etM.text.toString(), etC.text.toString()),
                        "x",
                        etX.text.toString()
                    )
                }
            }
        )

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                inputBase.selected = v as EditText
            }
        }

        etA.onFocusChangeListener = listener
        etB.onFocusChangeListener = listener
        etM.onFocusChangeListener = listener
        etC.onFocusChangeListener = listener
        etX.onFocusChangeListener = listener
        etY.onFocusChangeListener = listener
    }
}