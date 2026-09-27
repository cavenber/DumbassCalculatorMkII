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
import kotlin.math.pow

class DistanceFormula : Fragment() {

    lateinit var etA: EditText
    lateinit var etB: EditText
    lateinit var etD: EditText

    lateinit var inputBase: InputBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_distance_formula, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etA = view.findViewById<EditText>(R.id.df_a)
        etB = view.findViewById<EditText>(R.id.df_b)
        etD = view.findViewById<EditText>(R.id.df_d)

        etA.showSoftInputOnFocus = false
        etB.showSoftInputOnFocus = false

        inputBase = InputBase(
            view, requireContext(),
            {
                etA.setText("")
                etB.setText("")
                etD.setText("")
            },
            {
                val a = Num.evalMultiToNum(etA.text.toString())
                val b = Num.evalMultiToNum(etB.text.toString())

                val d = ((b[0] - a[0]).pow(2.0) + (b[1] - a[1]).pow(2.0)).pow(0.5)

                etD.setText(Num.toString(d))
            },
            {
                DBHelper(requireContext()).saveAnswer(
                    "Distance Formula",
                    String.format("A(%s) | B(%s)", etA.text.toString(), etB.text.toString()),
                    "d",
                    etD.text.toString()
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