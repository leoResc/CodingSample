package com.leo.project.decide

import com.leo.project.NO_SUCH_ELEMENT_EXCEPTION
import com.leo.project.io.BooleanAnswer
import com.leo.project.io.InputReader
import com.leo.project.io.NumericAnswer
import com.leo.project.io.OutputWriter
import com.leo.project.io.UserAnswer
import com.leo.project.objects.BooleanQuestion
import com.leo.project.objects.NumericQuestion
import com.leo.project.objects.ProseQuestion
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DiscussionTopicTest {

    private val inputMock: InputReader = mockk()
    private val outputMock: OutputWriter = mockk(relaxed = true)

    @BeforeEach
    fun init() {
        clearMocks(inputMock, outputMock)
    }

    @Test
    fun `should return invalid String answer`() { //TODO: fix loop of READ attempts for mock - write own stub
        val expectedAnswer = UserAnswer.Invalid(NO_SUCH_ELEMENT_EXCEPTION)
        every { inputMock.readEverything() } returns expectedAnswer
        val question = ProseQuestion("Why are firetrucks red?")

        val answer = DiscussionTopic(question, inputMock, outputMock).reachAgreementAndReturnAnswer()

        assertThat(answer).isEqualTo(expectedAnswer)
    }

    @Nested
    inner class ValidCases {

        @Test
        fun `should return valid String answer`() {
            val expectedAnswer = UserAnswer.Valid("Because fire is red")
            every { inputMock.readEverything() } returns expectedAnswer
            val question = ProseQuestion("Why are firetrucks red?")

            val answer = DiscussionTopic(question, inputMock, outputMock).reachAgreementAndReturnAnswer()

            assertThat(answer).isEqualTo(expectedAnswer)
        }

        @Test
        fun `should return valid Boolean answer`() {
            val expectedAnswer = BooleanAnswer.Valid(true)
            every { inputMock.readBoolean() } returns expectedAnswer
            val question = BooleanQuestion("Are you happy?")

            val answer = DiscussionTopic(question, inputMock, outputMock).reachAgreementAndReturnAnswer()

            assertThat(answer).isEqualTo(expectedAnswer)
        }

        @Test
        fun `should return valid Numeric answer`() {
            val expectedAnswer = NumericAnswer.ValidInteger(42)
            every { inputMock.readInteger() } returns expectedAnswer
            val question = NumericQuestion("What number are you thinking of?")

            val answer = DiscussionTopic(question, inputMock, outputMock).reachAgreementAndReturnAnswer()

            assertThat(answer).isEqualTo(expectedAnswer)
        }

    }


}