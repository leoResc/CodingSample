package com.leo.project.io

sealed class UserAnswer {

    data class Valid(val value: String) : UserAnswer() {
        override fun userFriendlyValue() = value
    }

    data class Invalid(val exception: Throwable) : UserAnswer()

    object Help : UserAnswer() //Not implemented so far

    object Exit : UserAnswer() {
        override fun canContinue() = false
    }

    open fun canContinue() = true

    open fun userFriendlyValue(): String = toString()
}


sealed class BooleanAnswer : UserAnswer() { //This inheritance is allowed, because they are in the same file

    data class Valid(val value: Boolean) : BooleanAnswer() {
        override fun userFriendlyValue() = "$value"
    }

    data class Invalid(val exception: Throwable) : BooleanAnswer()
}


sealed class NumericAnswer : UserAnswer() {

    data class ValidInteger(val value: Int) : NumericAnswer() {
        override fun userFriendlyValue() = "$value"
    }

    data class InvalidNumber(val exception: Throwable) : NumericAnswer()
}

