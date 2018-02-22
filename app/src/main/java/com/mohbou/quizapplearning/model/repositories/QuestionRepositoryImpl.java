package com.mohbou.quizapplearning.model.repositories;

import com.mohbou.quizapplearning.model.entities.Answer;
import com.mohbou.quizapplearning.model.entities.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository {

    //test question simulation
    private List<Question> questions = new ArrayList<>();
    private List<Answer> answers;


    @Override
    public List<Question> loadQuestions() {

        for (int i = 0; i < 10; i++) {
            Question question =  new Question();
            question.setId(i);
            question.setStatement("This is a fake statement for Question number :"+i);

            answers = new ArrayList<>();

            for (int j = 0; j < 4; j++) {
                Answer answer = new Answer();
                answer.setStatement("This is one of the plausible answer "+j);
                answer.setCorrect(j % 2 == 0);
                answers.add(answer);
            }
            question.setAnswers(answers);
            questions.add(question);

        }

        return questions;
    }
}
