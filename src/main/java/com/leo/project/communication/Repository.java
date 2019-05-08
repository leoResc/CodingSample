package com.leo.project.communication;

import com.leo.project.objects.Question;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Fake repository
 * For the presentation purpose
 */
@Component
public class Repository {

    private final QuestionRepository questionRepository = new QuestionRepository();

    public List<Question> getAllQuestions() {
        return questionRepository.getAll();
    }

}
