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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class DiscussionTopicTest {

    private val inputMock: InputReader = mockk()
    private val outputMock: OutputWriter = mockk(relaxed = true)

    @BeforeEach
    fun init() {
        clearMocks(inputMock, outputMock)
    }

    @Test
    fun `should return immediately EXIT String answer`() {
        val expectedAnswer = UserAnswer.Exit
        every { inputMock.readEverything() } returns expectedAnswer
        val question = ProseQuestion("Why are firetrucks red?")

        val answer = DiscussionTopic(question, inputMock, outputMock).reachAgreementAndReturnAnswer()

        assertThat(answer).isEqualTo(expectedAnswer)
    }


    /**
     * The {@link Discussion} is over, when the question was answered properly, or EXIT is written. Until then,
     * it repeats the question and waits for answers.
     */
    @Nested
    inner class MultipleTry {

        @Test
        fun `should return EXIT answer on second try, after giving invalid String answer on first try`() {
            //Mockito is used here, because it can mock sequential behaviour. Here it returns first an invalid answer
            // and on second time a valid/exit one
            val inputBehaviourMock = mock(InputReader::class.java)
            `when`(inputBehaviourMock.readEverything())
                    .thenReturn(UserAnswer.Invalid(NO_SUCH_ELEMENT_EXCEPTION))
                    .thenReturn(UserAnswer.Exit)

            val question = ProseQuestion("Why are firetrucks red?")

            val answer = DiscussionTopic(question, inputBehaviourMock, outputMock).reachAgreementAndReturnAnswer()

            assertThat(answer).isEqualTo(UserAnswer.Exit)
        }

        @Test
        fun `should return Valid answer on second try, after giving invalid String answer on first try`() {
            val expectedValidAnswer = UserAnswer.Valid("Because its sunday.")
            //Mockito is used here, because it can mock sequential behaviour. Here it returns first an invalid answer
            // and on second time a valid/exit one
            val inputBehaviourMock = mock(InputReader::class.java)
            `when`(inputBehaviourMock.readEverything())
                    .thenReturn(UserAnswer.Invalid(NO_SUCH_ELEMENT_EXCEPTION))
                    .thenReturn(expectedValidAnswer)

            val question = ProseQuestion("Why are firetrucks red?")

            val answer = DiscussionTopic(question, inputBehaviourMock, outputMock).reachAgreementAndReturnAnswer()

            assertThat(answer).isEqualTo(expectedValidAnswer)
        }
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