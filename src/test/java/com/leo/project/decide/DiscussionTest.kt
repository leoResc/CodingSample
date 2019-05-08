package com.leo.project.decide

import com.leo.project.io.SystemOutput
import com.leo.project.io.UserInput
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DiscussionTest {

    private val inputMock: UserInput = mockk()
    private val outputMock: SystemOutput = mockk()

    @BeforeEach
    fun init() {
        clearMocks(inputMock, outputMock)
    }

    @Test
    fun `should exit immediately when initialised without questions`() {
        every { outputMock.write(any()) } returns Unit
        val discussion = Discussion(outputMock, emptyList())

        discussion.initiateUntilOver()

        verify(exactly = 0) { inputMock.readEverything() }
        verify(exactly = 1) { outputMock.write(any()) }
    }

}