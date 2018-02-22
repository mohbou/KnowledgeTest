package com.mohbou.quizapplearning.dependecies;

import com.mohbou.quizapplearning.model.interactor.MainActivityInteractor;
import com.mohbou.quizapplearning.model.repositories.QuestionRepository;
import com.mohbou.quizapplearning.model.repositories.QuestionRepositoryImpl;
import com.mohbou.quizapplearning.ui.MVPMainActivityInterface;
import com.mohbou.quizapplearning.ui.MainActivityPresenter;

import javax.inject.Named;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@Module
public class MainModule {

    @Provides
    @ApplicationQuizScope
    public QuestionRepository provideRepository() {
        return new QuestionRepositoryImpl();
    }

    @Provides
    @Named("mainThread")
    public Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }
    @Provides
    @Named("ioScheduler")
    public Scheduler provideIOScheduler() {
        return Schedulers.io();
    }

    @Provides
    @ApplicationQuizScope
    public MVPMainActivityInterface.Model provideModel(QuestionRepository repository) {
        return new MainActivityInteractor(repository);
    }

    @Provides
    @ApplicationQuizScope
    public MVPMainActivityInterface.Presenter providePresenter(MVPMainActivityInterface.Model model, @Named("mainThread") Scheduler mainScheduler, @Named("ioScheduler") Scheduler ioScheduler) {
        return new MainActivityPresenter(model,mainScheduler,ioScheduler);
    }
    @Provides
    public CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
