package com.mohbou.quizapplearning.ui;

import com.mohbou.quizapplearning.model.entities.Question;
import com.mohbou.quizapplearning.model.repositories.QuestionRepository;

import java.util.Collections;
import java.util.List;


public class InMemoryRpositoryMock implements QuestionRepository {
    @Override
    public List<Question> loadQuestions() {
        return Collections.emptyList();
    }
}
