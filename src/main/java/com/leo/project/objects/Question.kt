package com.leo.project.objects

abstract class Question(open val message: String)

class BooleanQuestion(message: String) : Question(message) {

    override val message: String = "$message (true / false)"
}

data class ProseQuestion(override val message: String) : Question(message)

class NumericQuestion(message: String) : Question(message) {

    override val message: String = "$message (1...100)"
}