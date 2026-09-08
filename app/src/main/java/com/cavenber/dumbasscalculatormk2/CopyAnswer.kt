package com.cavenber.dumbasscalculatormk2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

class CopyAnswer {
    companion object {
        fun mostRecent(context: Context) {
            try {
                val answer = DBHelper(context).getMostRecentAnswer()
                if (answer != "") {
                    copyToClipboard(context, "Answer", answer)
                    Toast.makeText(context, "Answer has been successfully copied",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "There is no answer to copy. Please make use of Dumbass Calculator and try again",
                        Toast.LENGTH_LONG).show()
                }
            } catch (e: RuntimeException) {
                Toast.makeText(context, "I cannot copy your answer for some reason. You did something, didn't you", Toast.LENGTH_LONG).show()
            }

        }

        private fun copyToClipboard(context: Context, label: String, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(label, text)
            clipboard.setPrimaryClip(clip)
        }
    }

}