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
import com.cavenber.dumbasscalculatormk2.dependencies.InputSpecial
import com.cavenber.dumbasscalculatormk2.dependencies.Num

class ArithmeticOperation : Fragment() {

    lateinit var equation: EditText
    lateinit var answer: EditText

    lateinit var inputBase: InputBase
    lateinit var inputSpecial: InputSpecial

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_arithmetic_operation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        equation = view.findViewById<EditText>(R.id.equation)
        answer = view.findViewById<EditText>(R.id.answer)

        inputBase = InputBase(
            view, requireContext(),
            {
                equation.setText("")
                answer.setText("")
            },
            {
                answer.setText(Num.toString(Num.evalToNum(equation.text.toString())))
            },
            {
                DBHelper(requireContext()).saveAnswer(
                    "Arithmetic Operation",
                    equation.text.toString(),
                    "",
                    answer.text.toString()
                )
            }
        )
        inputBase.selected = equation
        inputBase.etEmpty = answer

        inputSpecial = InputSpecial(view)
        inputSpecial.selected = equation
    }
}