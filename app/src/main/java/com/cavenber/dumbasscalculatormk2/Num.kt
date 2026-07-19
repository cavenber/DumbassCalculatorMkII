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
            if (str == "2+2") { // easter egg for the radiohead song 2+2=5
                return 5.0
            }

            return Keval.eval(str)
        }
    }
}