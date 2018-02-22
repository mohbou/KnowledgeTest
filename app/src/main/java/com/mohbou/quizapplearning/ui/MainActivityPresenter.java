package com.mohbou.quizapplearning.ui;

import android.support.annotation.Nullable;

import com.mohbou.quizapplearning.model.entities.Answer;
import com.mohbou.quizapplearning.model.entities.Question;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;


public class MainActivityPresenter implements MVPMainActivityInterface.Presenter {

    private CompositeDisposable mCompositeDisposable;

    @Nullable
    MVPMainActivityInterface.View view;

    MVPMainActivityInterface.Model interactor;
    private final Scheduler mainScheduler;
    private final Scheduler ioScheduler;

    private int currentIndex = -1;

    private List<Question> questionsQuiz = Collections.emptyList();

    public MainActivityPresenter(MVPMainActivityInterface.Model interactor, Scheduler mainScheduler, Scheduler ioScheduler) {
        this.interactor = interactor;
        this.mainScheduler = mainScheduler;
        this.ioScheduler = ioScheduler;
    }


    @Override
    public void setView(MVPMainActivityInterface.View view) {
        this.view = view;
    }

    @Override
    public void loadQuiz() {

        mCompositeDisposable.add(interactor.loadQuestions()
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribeWith(new DisposableSingleObserver<List<Question>>() {

                    @Override
                    public void onSuccess(List<Question> questions) {
                        if (!questions.isEmpty()) {
                            questionsQuiz = questions;
                            loadQuestion(true);
                        } else {
                            view.displayNoQuestionAvailable();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.displayNoQuestionAvailable();
                    }
                }));


    }

    @Override
    public void loadQuestion(boolean forward) {
        if (forward) {
            if (isMoreQuestion()) {

                view.displayQuestion(getQuestions().get(++currentIndex));
                updateViewForward();
            }
        } else {
            if (notFirstQuestion()) {
                view.displayQuestion(getQuestions().get(--currentIndex));
                updateViewBackward();
            }
        }
    }

    @Override
    public void updateQuestion(Question question) {
        if (questionsQuiz.contains(question)) {
            questionsQuiz.set(questionsQuiz.indexOf(question), question);
        }
    }

    @Override
    public void assertAnswers(Question question, boolean forward) {

        Optional<Boolean> isAnswerSelected = question
                .getAnswers()
                .stream()
                .map(Answer::isSelected)
                .filter(x -> x)
                .findFirst();

        if (isAnswerSelected.isPresent() && isAnswerSelected.get()) {
            if(forward) {
                 view.next();
            }
            else {
                view.previous();
            }

        } else {
            view.noAnswerSelected();
        }


    }

    @Override
    public List<Question> getQuestions() {
        return questionsQuiz;
    }

    @Override
    public void setQuestions(List<Question> questions) {
        this.questionsQuiz =questions;
    }

    @Override
    public void dispose() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void setDisposable(CompositeDisposable mCompositeDisposable) {
        this.mCompositeDisposable = mCompositeDisposable;
    }

    private boolean notFirstQuestion() {
        return currentIndex > 0;
    }

    private void updateViewBackward() {
        if (isFirstQuestion()) {
            view.allowNext(true);
            view.allowPrevious(false);
        } else {
            view.allowNext(true);
            view.allowPrevious(true);
        }
    }

    private void updateViewForward() {
        if (isLastQuestion()) {
            view.allowNext(false);
        } else updateViewBackward();
    }

    private boolean isFirstQuestion() {
        return currentIndex == 0;
    }

    private boolean isLastQuestion() {
        return currentIndex == getQuestions().size() - 1;
    }

    private boolean isMoreQuestion() {
        return currentIndex < getQuestions().size() - 1;
    }


}
