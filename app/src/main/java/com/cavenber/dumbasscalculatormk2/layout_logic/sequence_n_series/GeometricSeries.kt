package com.cavenber.dumbasscalculatormk2.layout_logic.sequence_n_series

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
import kotlin.math.log

class GeometricSeries : Fragment() {

    lateinit var etT1: EditText
    lateinit var etT2: EditText
    lateinit var etN: EditText
    lateinit var etTn: EditText
    lateinit var etSn: EditText

    lateinit var inputBase: InputBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_geometric_series, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etT1 = view.findViewById<EditText>(R.id.gsr_t1)
        etT2 = view.findViewById<EditText>(R.id.gsr_t2)
        etN = view.findViewById<EditText>(R.id.gsr_n)
        etTn = view.findViewById<EditText>(R.id.gsr_tn)
        etSn = view.findViewById<EditText>(R.id.gsr_sn)

        etT1.showSoftInputOnFocus = false
        etT2.showSoftInputOnFocus = false
        etN.showSoftInputOnFocus = false
        etTn.showSoftInputOnFocus = false

        inputBase = InputBase(
            view, requireContext(),
            {
                etT1.setText("")
                etT2.setText("")
                etN.setText("")
                etTn.setText("")
                etSn.setText("")
            },
            {
                if (etTn.text.toString().isEmpty()) {
                    val t1 = Num.evalToNum(etT1.text.toString())
                    val t2 = Num.evalToNum(etT2.text.toString())
                    val n = Num.evalToNum(etN.text.toString())
                    inputBase.etEmpty = etTn

                    val a = t1
                    val r = t2 / t1
                    val sn = (a * (1 - (Math.pow(r, n)))) / (1 - r)

                    etSn.setText(Num.toString(sn))

                } else if (etN.text.toString().isEmpty()) {
                    val t1 = Num.evalToNum(etT1.text.toString())
                    val t2 = Num.evalToNum(etT2.text.toString())
                    val tn = Num.evalToNum(etTn.text.toString())
                    inputBase.etEmpty = etN

                    val a = t1
                    val r = t2 / t1
                    val n = log(tn / a, r) + 1
                    val Sn = (a * (1 - (Math.pow(r, n)))) / (1 - r)

                    etSn.setText(Num.toString(Sn))

                } else {
                    throw RuntimeException("go to catch block")
                }
            },
            {
                if (inputBase.etEmpty == etTn) {
                    DBHelper(requireContext()).saveAnswer(
                        "Geometric Series",
                        String.format(
                            "T(1) = %s | T(2) = %s | n = %s",
                            etT1.text.toString(),
                            etT2.text.toString(),
                            etN.text.toString()
                        ),
                        "S(n)",
                        etSn.text.toString()
                    )
                } else if (inputBase.etEmpty == etN) {
                    DBHelper(requireContext()).saveAnswer(
                        "Geometric Series",
                        String.format(
                            "T(1) = %s | T(2) = %s | T(n) = %s",
                            etT1.text.toString(),
                            etT2.text.toString(),
                            etTn.text.toString()
                        ),
                        "S(n)",
                        etSn.text.toString()
                    )
                }
            }
        )

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                inputBase.selected = v as EditText
            }
        }

        etT1.onFocusChangeListener = listener
        etT2.onFocusChangeListener = listener
        etN.onFocusChangeListener = listener
        etTn.onFocusChangeListener = listener
    }
}