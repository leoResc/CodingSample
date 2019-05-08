package com.leo.project.io

/**
 * Reads the input from user
 */
interface InputReader {
    fun readBoolean(): BooleanAnswer
    fun readInteger(): NumericAnswer

    /**
     * Reads input with all supported characters
     */
    fun readEverything(): UserAnswer
}