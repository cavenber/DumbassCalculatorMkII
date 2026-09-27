package com.cavenber.dumbasscalculatormk2.layout_logic.basic_calculations

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
import kotlin.math.pow

class QuadraticEquation : Fragment() {

    lateinit var etA: EditText
    lateinit var etB: EditText
    lateinit var etC: EditText
    lateinit var etX: EditText

    lateinit var inputBase: InputBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quadratic_equation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etA = view.findViewById<EditText>(R.id.qe_a)
        etB = view.findViewById<EditText>(R.id.qe_b)
        etC = view.findViewById<EditText>(R.id.qe_c)
        etX = view.findViewById<EditText>(R.id.qe_x)

        etA.showSoftInputOnFocus = false
        etB.showSoftInputOnFocus = false
        etC.showSoftInputOnFocus = false

        inputBase = InputBase(
            view, requireContext(),
            {
                etA.setText("")
                etB.setText("")
                etC.setText("")
                etX.setText("")
            },
            {
                val a: Double = Num.evalToNum(etA.text.toString())
                val b: Double = Num.evalToNum(etB.text.toString())
                val c: Double = Num.evalToNum(etC.text.toString())

                val discriminant = b.pow(2) - (4 * a * c)

                if (discriminant < 0) {
                    etX.setText("No Real Roots")
                } else if (discriminant == 0.0) {
                    val x = ((-b + (b.pow(2) - 4 * (a * c)).pow(0.5)) / (2 * a))

                    etX.setText(Num.toString(x))

                } else if (discriminant > 0) {
                    val x1 = (-b + (b.pow(2) - 4 * a * c).pow(0.5)) / (2 * a)
                    val x2 = (-b - (b.pow(2) - 4 * a * c).pow(0.5)) / (2 * a)

                    etX.setText(String.format("%s, %s", Num.toString(x1), Num.toString(x2)))
                } else {
                    throw RuntimeException("to go to catch block")
                }
            },
            {
                DBHelper(requireContext()).saveAnswer(
                    "Quadratic Equation",
                    String.format(
                        "a = %s | b = %s | c = %s",
                        etA.text.toString(),
                        etB.text.toString(),
                        etC.text.toString()
                    ),
                    "x",
                    etX.text.toString()
                )
            }
        )
        inputBase.etEmpty = etX

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                inputBase.selected = v as EditText
            }
        }

        etA.onFocusChangeListener = listener
        etB.onFocusChangeListener = listener
        etC.onFocusChangeListener = listener

    }
}