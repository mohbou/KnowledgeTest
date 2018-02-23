package com.mohbou.quizapplearning.ui;

import com.mohbou.quizapplearning.App;
import com.mohbou.quizapplearning.dependecies.ApplicationComponent;
import com.mohbou.quizapplearning.dependecies.ApplicationModule;
import com.mohbou.quizapplearning.dependecies.DaggerApplicationComponent;
import com.mohbou.quizapplearning.dependecies.MainModule;


public class MyAppMock extends App {

    public boolean empty;

    public ApplicationComponent getComponent() {
        if(empty) {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(MyAppMock.this))
                .mainModule(new MainMockModule())
                .build();}
        else {
            return DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(MyAppMock.this))
                    .mainModule(new MainModule())
                    .build();
        }
    }
}
