package com.cavenber.dumbasscalculatormk2

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class AnswerLog : Fragment() {

    lateinit var listLog: ListView
    lateinit var tvEmpty: TextView

    lateinit var btnRemoveAll: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.answer_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listLog = view.findViewById<ListView>(R.id.lv_list_log)
        tvEmpty = view.findViewById<TextView>(R.id.tv_empty)

        val entries: List<AnswerLogEntry> = DBHelper(requireContext()).getAllAnswerLogs()

        if (entries.isEmpty()) {
            listLog.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        } else {
            listLog.adapter = AnswerLogAdapter(requireContext(), entries)
        }

        btnRemoveAll = view.findViewById<Button>(R.id.btn_remove_all)
        btnRemoveAll.setOnClickListener {
            DBHelper(requireContext()).deleteAllAnswer()
            listLog.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        }
    }

    private class AnswerLogAdapter(
        context: Context,
        private val items: List<AnswerLogEntry>
    ) : ArrayAdapter<AnswerLogEntry>(context, R.layout.list_item_answer, items) {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView?: LayoutInflater.from(context)
                .inflate(R.layout.list_item_answer, parent, false)

            val item = items[position]

            view.findViewById<TextView>(R.id.tv_program).text = "${item.program}"
            view.findViewById<TextView>(R.id.tv_equation).text = "${item.equation}"

            if (item.answerVar == "") {
                view.findViewById<TextView>(R.id.tv_answer).text = "Answer: ${item.answer}"
            } else {
                view.findViewById<TextView>(R.id.tv_answer).text = "Answer: ${item.answerVar} = ${item.answer}"
            }


            return view
        }
    }

}