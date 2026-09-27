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

class LineSlope : Fragment() {

    lateinit var etA: EditText
    lateinit var etB: EditText
    lateinit var etM: EditText

    lateinit var inputBase: InputBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_line_slope, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etA = view.findViewById<EditText>(R.id.ls_a)
        etB = view.findViewById<EditText>(R.id.ls_b)
        etM = view.findViewById<EditText>(R.id.ls_m)

        etA.showSoftInputOnFocus = false
        etB.showSoftInputOnFocus = false

        inputBase = InputBase(
            view, requireContext(),
            {
                etA.setText("")
                etB.setText("")
                etM.setText("")
            },
            {
                val a = Num.evalMultiToNum(etA.text.toString())
                val b = Num.evalMultiToNum(etB.text.toString())

                val x = (b[1] - a[1]) / (b[0] - a[0])

                try {
                    etM.setText(Num.toString(x))
                } catch (e: ArithmeticException) {
                    etM.setText("undefined")
                }
            },
            {
                DBHelper(requireContext()).saveAnswer(
                    "Line Slope",
                    String.format("A(%s) | B(%s)", etA.text.toString(), etB.text.toString()),
                    "m",
                    etM.text.toString()
                )
            }
        )

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                inputBase.selected = v as EditText
            }
        }

        etA.onFocusChangeListener = listener
        etB.onFocusChangeListener = listener
    }
}