package com.mohbou.quizapplearning.ui;

import android.os.IBinder;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Root;
import android.support.test.rule.ActivityTestRule;
import android.view.WindowManager;

import com.mohbou.quizapplearning.App;
import com.mohbou.quizapplearning.R;
import com.mohbou.quizapplearning.dependecies.ApplicationComponent;
import com.mohbou.quizapplearning.dependecies.ApplicationModule;
import com.mohbou.quizapplearning.dependecies.DaggerApplicationComponent;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
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

    @Before
    public void setup() {
        ApplicationComponent component;

        MyAppMock myApp = (MyAppMock) InstrumentationRegistry
                          .getInstrumentation()
                          .getTargetContext()
                           .getApplicationContext();

        component = myApp.getComponent();

    }

    @Test
    public void questionsNotFound() {
        activityRule.launchActivity(null);

        onView(withId(R.id.next_button)).check(matches(not(isDisplayed())));
        onView(withId(R.id.previous_button)).check(matches(not(isDisplayed())));
        onView(withText(R.string.no_question_found)).inRoot(new ToastMatcher()).check(matches(withText("No question available")));
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