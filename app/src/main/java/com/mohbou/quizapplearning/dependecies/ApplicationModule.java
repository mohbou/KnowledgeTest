package com.mohbou.quizapplearning.dependecies;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationQuizScope
    public Context provideContext() {
        return  application;
    }
}
