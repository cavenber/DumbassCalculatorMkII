package com.cavenber.dumbasscalculatormk2

import com.notkamui.keval.Keval

class Num {
    companion object {
        fun toString(num: Double) : String {
            if (num % 1.0 == 0.0) {
                return num.toInt().toString()
            } else {
                return num.toString()
            }
        }

        fun evalToNum(str: String) : Double {
            var string = str

            if (string == "2+2") { // easter egg for the radiohead song 2+2=5
                return 5.0
            }

            var openBracketCount = string.count{ it == '(' }
            var closeBracketCount = string.count { it == ')' }

            while (closeBracketCount < openBracketCount) {
                string += ")"

                openBracketCount = string.count{ it == '(' }
                closeBracketCount = string.count { it == ')' }
            }

            return Keval.eval(string)
        }

        fun evalMultiToNum(string: String) : List<Double> {
            val parts = string.split(Regex(",(?=(?:[^()]*\\([^()]*\\))*[^()]*$)"))

            var nums = mutableListOf<Double>()

            for (i in parts.indices) {
                nums.add(evalToNum(parts[i]))
            }

            return nums
        }
    }
}