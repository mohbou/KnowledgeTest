package com.mohbou.quizapplearning.model.interactor;

import com.mohbou.quizapplearning.model.entities.Question;
import com.mohbou.quizapplearning.model.repositories.QuestionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityInteractorTest {
    @Mock
    QuestionRepository repository;

    MainActivityInteractor interactor;
    private List<Question> value;

    @Before
    public void setup() {
        interactor = new MainActivityInteractor(repository);
    }

    @Test
    public void shouldPassQuestionsToPresenter() {

        value = Arrays.asList(new Question());

//        when(interactor.loadQuestions()).thenReturn(Single.fromCallable(new Callable<List<Question>>() {
//            @Override
//            public List<Question> call() throws Exception {
//                return repository.loadQuestions();
//            }
//        }));


    }

    @Test
    public void questionsNotFound() {
        when(repository.loadQuestions()).thenReturn(new ArrayList<Question>());

//        assertTrue(interactor.loadQuestions().size() == 0);
    }

}