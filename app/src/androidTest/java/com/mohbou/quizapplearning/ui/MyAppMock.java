package com.mohbou.quizapplearning.ui;

import android.app.Application;


import com.mohbou.quizapplearning.App;
import com.mohbou.quizapplearning.dependecies.ApplicationComponent;
import com.mohbou.quizapplearning.dependecies.ApplicationModule;
import com.mohbou.quizapplearning.dependecies.DaggerApplicationComponent;


public class MyAppMock extends App {



    public ApplicationComponent getComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(MyAppMock.this))
                .mainModule(new MainMockModule())
                .build();
    }
}
