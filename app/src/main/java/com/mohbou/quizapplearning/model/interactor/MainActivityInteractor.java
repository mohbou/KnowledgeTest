package com.mohbou.quizapplearning.model.interactor;

import com.mohbou.quizapplearning.model.entities.Question;
import com.mohbou.quizapplearning.model.repositories.QuestionRepository;
import com.mohbou.quizapplearning.ui.MVPMainActivityInterface;


import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;


public class MainActivityInteractor implements MVPMainActivityInterface.Model {

    private final QuestionRepository repository;

    public MainActivityInteractor(QuestionRepository repository) {

        this.repository = repository;
    }

    @Override
    public Single<List<Question>> loadQuestions() {
        return Single.fromCallable(new Callable<List<Question>>() {
            @Override
            public List<Question> call() throws Exception {
                return repository.loadQuestions();
            }
        });

    }
}
