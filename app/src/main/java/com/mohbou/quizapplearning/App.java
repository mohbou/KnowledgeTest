package com.mohbou.quizapplearning;

import android.app.Application;

import com.mohbou.quizapplearning.dependecies.ApplicationComponent;
import com.mohbou.quizapplearning.dependecies.ApplicationModule;
import com.mohbou.quizapplearning.dependecies.DaggerApplicationComponent;


public class App extends Application {

    ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
