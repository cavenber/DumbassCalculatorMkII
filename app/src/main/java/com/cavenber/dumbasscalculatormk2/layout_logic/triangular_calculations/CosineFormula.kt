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
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.pow

class CosineFormula : Fragment() {

    lateinit var eta: EditText
    lateinit var etb: EditText
    lateinit var etC: EditText
    lateinit var etc: EditText

    lateinit var tgDegree: ToggleButton

    lateinit var inputBase: InputBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cosine_formula, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eta = view.findViewById<EditText>(R.id.cf_a)
        etb = view.findViewById<EditText>(R.id.cf_b)
        etC = view.findViewById<EditText>(R.id.cf_C)
        etc = view.findViewById<EditText>(R.id.cf_c)

        tgDegree = view.findViewById<ToggleButton>(R.id.cf_tg_degree)

        eta.showSoftInputOnFocus = false
        etb.showSoftInputOnFocus = false
        etC.showSoftInputOnFocus = false
        etc.showSoftInputOnFocus = false

        inputBase = InputBase(
            view, requireContext(),
            {
                eta.setText("")
                etb.setText("")
                etC.setText("")
                etc.setText("")
            },
            {
                if (etc.text.toString().isEmpty()) {
                    val a = Num.evalToNum(eta.text.toString())
                    val b = Num.evalToNum(etb.text.toString())
                    var C = Num.evalToNum(etC.text.toString())
                    inputBase.etEmpty = etc

                    if (tgDegree.isChecked) {
                        C = Math.toRadians(C)
                    }

                    val c = (a.pow(2) + b.pow(2) - (2 * a * b * cos(C))).pow(0.5)

                    etc.setText(Num.toString(c))

                } else if (etC.text.toString().isEmpty()) {
                    val a = Num.evalToNum(eta.text.toString())
                    val b = Num.evalToNum(etb.text.toString())
                    val c = Num.evalToNum(etc.text.toString())
                    inputBase.etEmpty = etC

                    var C = acos((a.pow(2) + b.pow(2) - c.pow(2)) / (2 * a * b))

                    if (tgDegree.isChecked) {
                        C = Math.toDegrees(C)
                    }

                    etC.setText(Num.toString(C))

                } else {
                    throw RuntimeException("to go to catch block")
                }
            },
            {
                if (inputBase.etEmpty == etc) {
                    DBHelper(requireContext()).saveAnswer(
                        "Cosine Formula",
                        String.format(
                            "a = %s | b = %s | C = %s",
                            eta.text.toString(),
                            etb.text.toString(),
                            etC.text.toString()
                        ),
                        "c",
                        etc.text.toString()
                    )
                } else if (inputBase.etEmpty == etC) {
                    DBHelper(requireContext()).saveAnswer(
                        "Cosine Formula",
                        String.format(
                            "a = %s | b = %s | c = %s",
                            eta.text.toString(),
                            etb.text.toString(),
                            etc.text.toString()
                        ),
                        "C",
                        etC.text.toString()
                    )
                }
            }
        )

        val listener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                inputBase.selected = v as EditText
            }
        }

        eta.onFocusChangeListener = listener
        etb.onFocusChangeListener = listener
        etC.onFocusChangeListener = listener
        etc.onFocusChangeListener = listener
    }
}