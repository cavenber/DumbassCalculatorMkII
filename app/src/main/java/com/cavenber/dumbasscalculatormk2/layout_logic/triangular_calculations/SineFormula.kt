package com.cavenber.dumbasscalculatormk2.layout_logic.triangular_calculations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.cavenber.dumbasscalculatormk2.R
import com.cavenber.dumbasscalculatormk2.dependencies.DBHelper
import com.cavenber.dumbasscalculatormk2.dependencies.InputBase
import com.cavenber.dumbasscalculatormk2.dependencies.Num
import kotlin.math.asin
import kotlin.math.sin

class SineFormula : Fragment() {

    lateinit var etA: EditText
    lateinit var eta: EditText
    lateinit var etB: EditText
    lateinit var etb: EditText

    lateinit var tgDegree: ToggleButton

    lateinit var inputBase: InputBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sine_formula, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etA = view.findViewById<EditText>(R.id.sf_A)
        eta = view.findViewById<EditText>(R.id.sf_a)
        etB = view.findViewById<EditText>(R.id.sf_B)
        etb = view.findViewById<EditText>(R.id.sf_b)

        tgDegree = view.findViewById<ToggleButton>(R.id.sf_tg_degree)

        etA.showSoftInputOnFocus = false
        eta.showSoftInputOnFocus = false
        etB.showSoftInputOnFocus = false
        etb.showSoftInputOnFocus = false

        inputBase = InputBase(
            view, requireContext(),
            {
                etA.setText("")
                eta.setText("")
                etB.setText("")
                etb.setText("")
            },
            {
                if (etb.text.toString().isEmpty()) {
                    var A = Num.evalToNum(etA.text.toString())
                    val a = Num.evalToNum(eta.text.toString())
                    var B = Num.evalToNum(etB.text.toString())
                    inputBase.etEmpty = etb

                    if (tgDegree.isChecked) {
                        A = Math.toRadians(A)
                        B = Math.toRadians(B)
                    }

                    val b = (a * sin(B)) / sin(A)

                    etb.setText(Num.toString(b))

                } else if (etB.text.toString().isEmpty()) {
                    var A = Num.evalToNum(etA.text.toString())
                    val a = Num.evalToNum(eta.text.toString())
                    val b = Num.evalToNum(etb.text.toString())
                    inputBase.etEmpty = etB

                    if (tgDegree.isChecked) {
                        A = Math.toRadians(A)
                    }

                    var B = asin((sin(A) * b) / a)

                    if (tgDegree.isChecked) {
                        B = Math.toDegrees(B)
                    }

                    etB.setText(Num.toString(B))

                } else {
                    throw RuntimeException("to go to catch block")
                }
            },
            {
                if (inputBase.etEmpty == etb) {
                    DBHelper(requireContext()).saveAnswer(
                        "Sine Formula",
                        String.format(
                            "A = %s | a = %s | B = %s",
                            etA.text.toString(),
                            eta.text.toString(),
                            etB.text.toString()
                        ),
                        "b",
                        etb.text.toString()
                    )
                } else if (inputBase.etEmpty == etB) {
                    DBHelper(requireContext()).saveAnswer(
                        "Sine Formula",
                        String.format(
                            "A = %s | a = %s | b = %s",
                            etA.text.toString(),
                            eta.text.toString(),
                            etb.text.toString()
                        ),
                        "B",
                        etB.text.toString()
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
        eta.onFocusChangeListener = listener
        etB.onFocusChangeListener = listener
        etb.onFocusChangeListener = listener
    }
}