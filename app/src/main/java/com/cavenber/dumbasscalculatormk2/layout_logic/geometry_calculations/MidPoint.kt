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

class MidPoint : Fragment() {

    lateinit var etA: EditText
    lateinit var etB: EditText
    lateinit var etM: EditText

    lateinit var inputBase: InputBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mid_point, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etA = view.findViewById<EditText>(R.id.mp_a)
        etB = view.findViewById<EditText>(R.id.mp_b)
        etM = view.findViewById<EditText>(R.id.mp_m)

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

                val mx = (a[0] + b[0]) / 2
                val my = (a[1] + b[1]) / 2

                etM.setText(String.format("%s,%s", Num.toString(mx), Num.toString(my)))
            },
            {
                DBHelper(requireContext()).saveAnswer(
                    "Mid-Point",
                    String.format("A(%s) | B(%s)", etA.text.toString(), etB.text.toString()),
                    "M(x,y)",
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