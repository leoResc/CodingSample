package com.leo.project.io

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
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
            clearScannerBuffer()
            val readValue = scanner.nextBoolean()
            BooleanAnswer.Valid(readValue)
        } catch (e: IllegalStateException) {
            BooleanAnswer.Invalid(e)
        } catch (e: NoSuchElementException) {
            BooleanAnswer.Invalid(e)
        }
    }

    override fun readInteger(): NumericAnswer { //FIXME: In this mode, user cant ask for help or exit app
        return try {
            clearScannerBuffer()
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
            exactStringAnswer(scanner.nextLine())
        } catch (e: IllegalStateException) {
            UserAnswer.Invalid(e)
        } catch (e: NoSuchElementException) {
            UserAnswer.Invalid(e)
        }
    }

    private fun exactStringAnswer(text: String) =
            when (text) {
                EXIT -> UserAnswer.Exit
                HELP -> UserAnswer.Help
                else -> UserAnswer.Valid(text)
            }

    private fun clearScannerBuffer() = runBlocking {
        // If we don't wrap it async, the withTimeoutOrNull won't be able to cancel the computation, because it would be
        // caught in an un-suspendable process!
        val scannerHasNext = async { scanner.hasNext() }
        val bufferIsSet = withTimeoutOrNull(100L) { scannerHasNext.await() } ?: false
        if (bufferIsSet)
            scanner.next()

    }
}

