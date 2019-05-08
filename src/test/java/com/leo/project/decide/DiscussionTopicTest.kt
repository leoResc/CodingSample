package com.leo.project.decide

import com.leo.project.io.InputReader
import com.leo.project.io.OutputWriter
import com.leo.project.io.UserAnswer
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
    private val outputMock: OutputWriter = mockk()

    @BeforeEach
    fun init() {
        clearMocks(inputMock, outputMock)
    }

    @Nested
    inner class ValidCases {

        @Test
        fun `should return valid String answer - text question got valid text answer`() {
            val expectedAnswer = UserAnswer.Valid("Because fire is red")
            every { outputMock.write(any()) } returns Unit
            every { inputMock.readEverything() } returns expectedAnswer
            val question = ProseQuestion("Why are firetrucks red?")

            val answer = DiscussionTopic(question, inputMock, outputMock).reachAgreementAndReturnAnswer()

            assertThat(answer).isEqualTo(expectedAnswer)
        }

    }


}