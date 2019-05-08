package com.leo.project.io

import java.util.*
import java.util.logging.*

const val EXIT = "exit"
const val HELP = "help"

/**
 * Basically a console input Reader
 */
class UserInput(private val scanner: Scanner = Scanner(System.`in`)) : InputReader {

    companion object {
        private val logger = Logger.getLogger(UserInput::class.java.simpleName)
    }

    override fun readBoolean(): BooleanAnswer { //FIXME: In this mode, user cant ask for help or exit app
        return try {
            val readValue = scanner.nextBoolean()
            BooleanAnswer.Valid(readValue)
        } catch (e: IllegalStateException) {
            BooleanAnswer.Invalid(e)
        } catch (e: NoSuchElementException) {
            BooleanAnswer.Invalid(e) //Booleans can't and shouldn't be empty
        }
    }

    override fun readInteger(): NumericAnswer { //FIXME: In this mode, user cant ask for help or exit app
        return try {
            val readValue = scanner.nextInt()
            NumericAnswer.ValidInteger(readValue)
        } catch (e: IllegalStateException) {
            NumericAnswer.InvalidNumber(e)
        } catch (e: NoSuchElementException) {
            NumericAnswer.InvalidNumber(e)
        }
    }

    override fun readEverything(): UserAnswer {
        return try {
            exactStringAnswer(scanner.next())
        } catch (e: IllegalStateException) {
            UserAnswer.Invalid(e)
        } catch (e: NoSuchElementException) {
            logNoInputFound(e)
            UserAnswer.Empty
        }
    }

    private fun exactStringAnswer(text: String) =
            when {
                text.isEmpty() -> UserAnswer.Empty
                text == EXIT -> UserAnswer.Exit
                text == HELP -> UserAnswer.Help
                else -> UserAnswer.Valid(text)
            }


    private fun logNoInputFound(e: Throwable) = logger.log(Level.FINE, "No input found", e)
}

