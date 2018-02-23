package com.mohbou.quizapplearning.ui;

import com.mohbou.quizapplearning.dependecies.ApplicationQuizScope;
import com.mohbou.quizapplearning.dependecies.MainModule;
import com.mohbou.quizapplearning.model.repositories.QuestionRepository;

import dagger.Provides;


public class MainMockModule extends MainModule {

    @Provides
    @ApplicationQuizScope
    public QuestionRepository provideRepository() {
        return new InMemoryRepositoryMockEmpty();
    }

}
