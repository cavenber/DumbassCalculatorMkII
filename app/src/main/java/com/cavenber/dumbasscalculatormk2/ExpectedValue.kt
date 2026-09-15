package com.cavenber.dumbasscalculatormk2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

class ExpectedValue : Fragment() {

    lateinit var etPossibility: EditText
    lateinit var tvValues: TextView
    lateinit var tvTotalProb: TextView
    lateinit var etAns: EditText

    lateinit var inputBase: InputBase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_expected_value, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etPossibility = view.findViewById<EditText>(R.id.ev_possibility)
        tvValues = view.findViewById<TextView>(R.id.ev_values)
        tvTotalProb = view.findViewById<TextView>(R.id.ev_total_probability)
        etAns = view.findViewById<EditText>(R.id.ev_ans)

        etPossibility.showSoftInputOnFocus = false
        etAns.showSoftInputOnFocus = false

        val expectedValue = mutableListOf<Double>()
        val possRecord = mutableListOf<String>()

        inputBase = InputBase(view, requireContext(),
            {
                etPossibility.setText("")
                tvTotalProb.text = "0.0"
                etAns.setText("")
            },
            {
                val poss = Num.evalMultiToNum(etPossibility.text.toString())
                val values = Num.evalMultiToNum(tvValues.text.toString()) + poss[0]
                val sumProb = tvTotalProb.text.toString().toDouble() + poss[1]

                expectedValue.add(poss[0] * poss[1])
                possRecord.add(etPossibility.text.toString())

                tvValues.text = values.toString()
                tvTotalProb.text = sumProb.toString()

                if (sumProb == 1.0) {
                    etAns.setText(expectedValue.sum().toString())
                } else if (sumProb < 1.0) {
                    throw DataIncompleteException()
                }
            },
            {
                DBHelper(requireContext()).saveAnswer(
                    "Expected Value",
                    possRecord.joinToString(separator = "; "),
                    "Expected Value",
                    etAns.text.toString()
                )
            }
        )
        inputBase.selected = etPossibility
        inputBase.etEmpty = etAns
    }
}