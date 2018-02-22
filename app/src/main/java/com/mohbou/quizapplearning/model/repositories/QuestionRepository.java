package com.mohbou.quizapplearning.model.repositories;

import com.mohbou.quizapplearning.model.entities.Question;

import java.util.List;

public interface QuestionRepository {

    List<Question> loadQuestions();
}
