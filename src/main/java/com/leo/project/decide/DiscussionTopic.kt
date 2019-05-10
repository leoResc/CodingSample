package com.leo.project.decide

import com.leo.project.io.BooleanAnswer
import com.leo.project.io.InputReader
import com.leo.project.io.NumericAnswer
import com.leo.project.io.OutputWriter
import com.leo.project.io.SystemOutput
import com.leo.project.io.UserAnswer
import com.leo.project.io.UserInput
import com.leo.project.objects.BooleanQuestion
import com.leo.project.objects.NumericQuestion
import com.leo.project.objects.ProseQuestion
import com.leo.project.objects.Question
import com.leo.project.util.COULD_NOT_READ
import java.util.logging.*

/**
 * Stateful class which takes care of one question
 */
class DiscussionTopic @JvmOverloads constructor(private val question: Question,
                                                private val reader: InputReader = UserInput(),
                                                private val writer: OutputWriter = SystemOutput()) {

    companion object {
        private val logger = Logger.getLogger(DiscussionTopic::class.java.simpleName)
    }

    fun reachAgreementAndReturnAnswer(): UserAnswer {
        writer.write(question.message)

        return when (val answer = answerToTheQuestion()) {
            is UserAnswer.Invalid -> logReadProblemAndRetry(answer.exception)
            is BooleanAnswer.Invalid -> logReadProblemAndRetry(answer.exception)
            is NumericAnswer.InvalidNumber -> logReadProblemAndRetry(answer.exception)
            else -> answer
        }
    }

    private fun logReadProblemAndRetry(throwable: Throwable): UserAnswer {
        logger.log(Level.FINE, throwable) { "Your answer is 'problematic'." }
        writer.write(COULD_NOT_READ)
        return reachAgreementAndReturnAnswer()
    }

    private fun answerToTheQuestion() =
            when (question) {
                is ProseQuestion -> reader.readEverything()
                is BooleanQuestion -> reader.readBoolean()
                is NumericQuestion -> reader.readInteger()
                else -> throw RuntimeException("Question type is not defined for $question")
            }
}