package com.mohbou.quizapplearning.ui;

import com.mohbou.quizapplearning.model.entities.Question;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.AdditionalMatchers.not;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {


    @Mock
    MVPMainActivityInterface.View view;

    @Mock
    MVPMainActivityInterface.Model model;


    MVPMainActivityInterface.Presenter presenter;

    @Before
    public void setup() {
        presenter = new MainActivityPresenter(model, Schedulers.trampoline(),Schedulers.trampoline());
        presenter.setView(view);
        presenter.setDisposable(new CompositeDisposable());
    }

    @Test
    public void shouldLoadQuestionToUser() {

        //given
        when(model.loadQuestions()).thenReturn(Single.just(Arrays.asList(new Question())));

        //when
        presenter.loadQuiz();

        //then
        verify(view, times(1)).displayQuestion((Question) anyObject());
        verify(view,never()).displayNoQuestionAvailable();

    }

    @Test
    public void shouldDisplayMessageWhenQuestionAreNotAvailable() {

        //given
        when(model.loadQuestions()).thenReturn(Single.just(Collections.<Question>emptyList()));

        //when
        presenter.loadQuiz();

        //then
//        verify(view,never()).displayQuestion((Question) anyObject());
        verify(view, times(1)).displayNoQuestionAvailable();
    }

    @Test
    public void shouldLoadNextQuestion() {
         //given
         final Question question = new Question();
         final Question question2 = new Question();
         List<Question> questions = Arrays.asList(question,question2);
         presenter.setQuestions(questions);

          presenter.loadQuestion(true);

        verify(view,times(1)).displayQuestion(question);

    }


    @After
    public void tearDown() {
        presenter.dispose();
    }

}