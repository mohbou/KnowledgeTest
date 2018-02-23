package com.mohbou.quizapplearning.ui;

import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.view.WindowManager;

import com.mohbou.quizapplearning.R;
import com.mohbou.quizapplearning.dependecies.ApplicationComponent;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;


public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(
                    MainActivity.class,
                    true,
                    false);
    private MyAppMock myApp;

    @Before
    public void setup() {
        ApplicationComponent component;

        myApp = (MyAppMock) InstrumentationRegistry
                          .getInstrumentation()
                          .getTargetContext()
                           .getApplicationContext();

        component = myApp.getComponent();

    }

    @Test
    public void questionsNotFound() {
        myApp.empty = true;
        activityRule.launchActivity(null);

        onView(withId(R.id.next_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.previous_button)).check(matches(not(isDisplayed())));
        onView(withText(R.string.no_question_found)).inRoot(new ToastMatcher()).check(matches(withText("No question available")));
    }

    @Test
    public void questionFoundWhenActivityStart() {
        myApp.empty = false;
        activityRule.launchActivity(null);

        onView(withId(R.id.next_button)).check(matches(isDisplayed()));
        onView(withId(R.id.previous_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.question_statement)).check(matches(withText("This is a fake statement for Question number :0")));

    }

    @Test
    public void shouldDisplayNextQuestionWhenClickNext() {
        myApp.empty = false;
        activityRule.launchActivity(null);


      onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.next_button)).check(matches(isDisplayed()))
                .perform(click());
      onView(withId(R.id.question_statement)).check(matches(withText("This is a fake statement for Question number :1")));

    }

    @Test
    public void shouldDisplayPreviousQuestionWhenClickPrevious() {
        myApp.empty = false;
        activityRule.launchActivity(null);
        onView(withId(R.id.previous_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.next_button)).check(matches(isDisplayed()))
                .perform(click());
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.previous_button)).perform(click());
        onView(withId(R.id.question_statement)).check(matches(withText("This is a fake statement for Question number :0")));


    }

    private class ToastMatcher extends TypeSafeMatcher<Root> {

        @Override
        public void describeTo(Description description) {
            description.appendText("is toast");
        }

        @Override
        public boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if ((type == WindowManager.LayoutParams.TYPE_TOAST)) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    return true;
                }
            }
            return false;
        }
    }
}