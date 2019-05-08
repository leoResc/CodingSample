package com.leo.project.io

/**
 * Wrapper for console writer <br>
 * Other classes are protected from inner Writer changes
 */
class SystemOutput : OutputWriter {

    override fun write(text: String) {
        System.out.println("System (Leo): $text")
    }

}