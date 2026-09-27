package com.cavenber.dumbasscalculatormk2.layout_logic.variations

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

class DirectVariation : Fragment() {

    lateinit var etX: EditText
    lateinit var etK: EditText
    lateinit var etY: EditText

    lateinit var inputBase: InputBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_direct_variation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etX = view.findViewById<EditText>(R.id.dv_x)
        etK = view.findViewById<EditText>(R.id.dv_k)
        etY = view.findViewById<EditText>(R.id.dv_y)

        etX.showSoftInputOnFocus = false
        etK.showSoftInputOnFocus = false
        etY.showSoftInputOnFocus = false

        inputBase = InputBase(
            view, requireContext(),
            {
                etX.setText("")
                etK.setText("")
                etY.setText("")
            },
            {
                if (etK.text.toString().isEmpty()) {
                    val x = Num.evalToNum(etX.text.toString())
                    val y = Num.evalToNum(etY.text.toString())
                    inputBase.etEmpty = etK

                    val k = y / x

                    etK.setText(Num.toString(k))

                } else if (etY.text.toString().isEmpty()) {
                    val x = Num.evalToNum(etX.text.toString())
                    val k = Num.evalToNum(etK.text.toString())
                    inputBase.etEmpty = etY

                    val y = x * k

                    etY.setText(Num.toString(y))

                } else if (etX.text.toString().isEmpty()) {
                    val k = Num.evalToNum(etK.text.toString())
                    val y = Num.evalToNum(etY.text.toString())
                    inputBase.etEmpty = etX

                    val x = y / k

                    etX.setText(Num.toString(x))

                } else {
                    throw RuntimeException("to go to catch block")
                }
            },
            {
                if (inputBase.etEmpty == etK) {
                    DBHelper(requireContext()).saveAnswer(
                        "Direct Variation",
                        String.format("x = %s | y = %s", etY.text.toString(), etX.text.toString()),
                        "k",
                        etK.text.toString()
                    )
                } else if (inputBase.etEmpty == etY) {
                    DBHelper(requireContext()).saveAnswer(
                        "Direct Variation",
                        String.format("x = %s | k = %s", etK.text.toString(), etX.text.toString()),
                        "y",
                        etY.text.toString()
                    )
                } else if (inputBase.etEmpty == etX) {
                    DBHelper(requireContext()).saveAnswer(
                        "Direct Variation",
                        String.format("k = %s | y = %s", etY.text.toString(), etK.text.toString()),
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

        etX.onFocusChangeListener = listener
        etK.onFocusChangeListener = listener
        etY.onFocusChangeListener = listener
    }
}