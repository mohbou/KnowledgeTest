package com.mohbou.quizapplearning.dependecies;

import com.mohbou.quizapplearning.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@ApplicationQuizScope
@Component(modules = {ApplicationModule.class,MainModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
}
