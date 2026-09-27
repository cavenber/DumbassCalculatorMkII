package com.cavenber.dumbasscalculatormk2.layout_logic.triangular_calculations

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

class PythagoreanTheorem : Fragment() {

    lateinit var etA: EditText
    lateinit var etB: EditText
    lateinit var etC: EditText

    lateinit var inputBase: InputBase

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

        inputBase = InputBase(
            view, requireContext(),
            {
                etA.setText("")
                etB.setText("")
                etC.setText("")
            },
            {
                if (etC.text.toString().isEmpty()) {
                    val a = Num.evalToNum(etA.text.toString())
                    val b = Num.evalToNum(etB.text.toString())
                    inputBase.etEmpty = etC

                    val c = (a.pow(2) + b.pow(2)).pow(0.5)

                    etC.setText(Num.toString(c))

                } else if (etB.text.toString().isEmpty()) {
                    val a = Num.evalToNum(etA.text.toString())
                    val c = Num.evalToNum(etC.text.toString())
                    inputBase.etEmpty = etB

                    val b = (c.pow(2) - a.pow(2)).pow(0.5)

                    etB.setText(Num.toString(b))

                } else if (etA.text.toString().isEmpty()) {
                    val b = Num.evalToNum(etB.text.toString())
                    val c = Num.evalToNum(etC.text.toString())
                    inputBase.etEmpty = etA

                    val a = (c.pow(2) - b.pow(2)).pow(0.5)

                    etA.setText(Num.toString(a))

                } else {
                    throw RuntimeException("to go to catch block")
                }
            },
            {
                if (inputBase.etEmpty == etC) {
                    DBHelper(requireContext()).saveAnswer(
                        "Pythagorean Theorem",
                        String.format("a = %s | b = %s", etA.text.toString(), etB.text.toString()),
                        "c",
                        etC.text.toString()
                    )
                } else if (inputBase.etEmpty == etB) {
                    DBHelper(requireContext()).saveAnswer(
                        "Pythagorean Theorem",
                        String.format("a = %s | c = %s", etA.text.toString(), etC.text.toString()),
                        "b",
                        etB.text.toString()
                    )
                } else if (inputBase.etEmpty == etA) {
                    DBHelper(requireContext()).saveAnswer(
                        "Pythagorean Theorem",
                        String.format("b = %s | c = %s", etB.text.toString(), etC.text.toString()),
                        "a",
                        etA.text.toString()
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
        etC.onFocusChangeListener = listener
    }
}