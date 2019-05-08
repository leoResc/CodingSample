package com.leo.project.io

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*

class UserInputTest {

    private val scannerMock: Scanner = mockk()
    private val noSuchElementException = NoSuchElementException("Element not found")

    @BeforeEach
    fun init() {
        // It is more performable to initialise a mock and clear it before each test, than having a 'lateinit var' field
        // and creating a new mock every single time
        clearMocks(scannerMock)
    }

    @Test
    fun `should return EXIT Answer`(){
        every { scannerMock.next() } returns "exit"

        val result = UserInput(scannerMock).readEverything()

        assertThat(result).isEqualTo(UserAnswer.Exit)
    }

    @Test
    fun `should return HELP Answer`(){
        every { scannerMock.next() } returns "help"

        val result = UserInput(scannerMock).readEverything()

        assertThat(result).isEqualTo(UserAnswer.Help)
    }

    @Nested
    inner class ValidCase {

        @Test
        fun `should return valid (String) Answer - characters and empty space`() {
            val expectedResult = "AbC dE"
            every { scannerMock.next() } returns expectedResult

            val result = UserInput(scannerMock).readEverything()

            assertThat(result).isEqualTo(UserAnswer.Valid(expectedResult))
        }

        @Test
        fun `should return valid (Integer) Answer`() {
            val expectedResult = 12345
            every { scannerMock.nextInt() } returns expectedResult

            val result = UserInput(scannerMock).readInteger()

            assertThat(result).isEqualTo(NumericAnswer.ValidInteger(expectedResult))
        }

        @Test
        fun `should return valid (Boolean) Answer`() {
            val expectedResult = true
            every { scannerMock.nextBoolean() } returns expectedResult

            val result = UserInput(scannerMock).readBoolean()

            assertThat(result).isEqualTo(BooleanAnswer.Valid(expectedResult))
        }

    }

    @Nested
    inner class InvalidCase {

        private val scannerClosedException = IllegalStateException("Scanner was closed")

        @Test
        fun `should return invalid (String) Answer with exception, because scanner was closed`() {
            every { scannerMock.next() } throws scannerClosedException

            val result = UserInput(scannerMock).readEverything()

            assertThat(result).isInstanceOf(UserAnswer.Invalid::class.java)
            assertThat((result as UserAnswer.Invalid).exception).isEqualTo(scannerClosedException)
        }

        @Test
        fun `should return invalid (Boolean) Answer with exception, because scanner was closed`() {
            every { scannerMock.nextBoolean() } throws scannerClosedException

            val result = UserInput(scannerMock).readBoolean()

            assertThat(result).isInstanceOf(BooleanAnswer.Invalid::class.java)
            assertThat((result as BooleanAnswer.Invalid).exception).isEqualTo(scannerClosedException)
        }

        @Test
        fun `should return invalid (Int) Answer with exception, because scanner was closed`() {
            every { scannerMock.nextInt() } throws scannerClosedException

            val result = UserInput(scannerMock).readInteger()

            assertThat(result).isInstanceOf(NumericAnswer.InvalidNumber::class.java)
            assertThat((result as NumericAnswer.InvalidNumber).exception).isEqualTo(scannerClosedException)
        }

        @Test
        fun `should return invalid (Int) Answer with exception, because input was not an Int`() {
            every { scannerMock.nextInt() } throws noSuchElementException

            val result = UserInput(scannerMock).readInteger()

            assertThat(result).isInstanceOf(NumericAnswer.InvalidNumber::class.java)
            assertThat((result as NumericAnswer.InvalidNumber).exception).isEqualTo(noSuchElementException)
        }

        @Test
        fun `should return invalid (Boolean) Answer with exception, because fitting element was not found`() {
            every { scannerMock.nextBoolean() } throws noSuchElementException

            val result = UserInput(scannerMock).readBoolean()

            assertThat(result).isInstanceOf(BooleanAnswer.Invalid::class.java)
            assertThat((result as BooleanAnswer.Invalid).exception).isEqualTo(noSuchElementException)
        }
    }

}