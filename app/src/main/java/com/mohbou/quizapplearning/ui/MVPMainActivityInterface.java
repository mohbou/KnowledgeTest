package com.mohbou.quizapplearning.ui;

import com.mohbou.quizapplearning.model.entities.Question;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public interface MVPMainActivityInterface {

    interface View {

        void displayQuestion(Question question);
        void displayNoQuestionAvailable();
        void allowNext(boolean next);
        void allowPrevious(boolean previous);
        void next();
        void previous();

        void noAnswerSelected();
    }
    interface Presenter {

        void loadQuiz();
        void setView(MVPMainActivityInterface.View view);
        void dispose();
        void setDisposable(CompositeDisposable mCompositeDisposable);

        void loadQuestion(boolean forward);

        void updateQuestion(Question question);

        void assertAnswers(Question question, boolean forward);

        List<Question> getQuestions();
        void setQuestions(List<Question> questions);
    }
    interface Model {

       Single<List<Question>> loadQuestions();
    }
}
