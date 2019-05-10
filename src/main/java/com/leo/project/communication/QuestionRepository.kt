package com.leo.project.communication

import com.leo.project.objects.BooleanQuestion
import com.leo.project.objects.NumericQuestion
import com.leo.project.objects.ProseQuestion
import com.leo.project.objects.Question
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class QuestionRepository {

    private val questions: List<Question> by lazy {
        listOf(
                BooleanQuestion("Do you feel tired?"),
                NumericQuestion("How many hours did you spend this week to do sport?"),
                ProseQuestion("What are you looking forward to today?"),
                BooleanQuestion("Are you an expert in anything?"),
                ProseQuestion("What did you eat today?"),
                NumericQuestion("How many stars are on the american flag?"),
                BooleanQuestion("Are you happy?")
        )
    }

    fun getAll(): List<Question> {
        runBlocking {
            // Simulation of a database call, or a HTTP request delay time
            delay(2000)
        }
        return questions
    }

}