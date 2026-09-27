package com.cavenber.dumbasscalculatormk2.dependencies

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

class Copier {
    companion object {
        fun mostRecentAnswer(context: Context) {
            try {
                val answer = DBHelper(context).getMostRecentAnswer()
                if (answer != "") {
                    copyToClipboard(context, "Answer", answer)
                    Toast.makeText(context, "Answer has been successfully copied",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "According to my records, you have never done a calculation in your life",
                        Toast.LENGTH_LONG).show()
                }
            } catch (e: RuntimeException) {
                Toast.makeText(context, "I cannot copy for some reason. You did something, didn't you", Toast.LENGTH_LONG).show()
            }

        }

        fun mostRecentCalculation(context: Context) {
            try {
                val calculation = DBHelper(context).getMostRecentCalculationLog()
                val answer: String

                if (calculation.answerVar == "") {
                    answer = "Answer: ${calculation.answer}"
                } else {
                    answer = "Answer: ${calculation.answerVar} = ${calculation.answer}"
                }

                if (calculation.id != (-1).toLong()) {
                    val string = String.format("%s\n%s\n%s", calculation.program, calculation.variables, answer)
                    copyToClipboard(context, "Calculation", string)
                    Toast.makeText(context, "Calculation has been successfully copied",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "According to my records, you have never done a calculation in your life",
                        Toast.LENGTH_LONG).show()
                }
            } catch (e: RuntimeException) {
                Toast.makeText(context, "I cannot copy for some reason. You did something, didn't you", Toast.LENGTH_LONG).show()
            }
        }

        private fun copyToClipboard(context: Context, label: String, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)
        }
    }
}