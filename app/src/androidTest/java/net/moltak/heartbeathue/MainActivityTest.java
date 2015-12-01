package net.moltak.heartbeathue;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by engeng on 12/1/15.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void buttonClickTest() {
        onView(withId(R.id.buttonChangeColor)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("1 times clicked!")));
    }

    @Test
    public void button20TimesClickTest() throws InterruptedException {
        for (int i = 0; i < 20; i ++) {
            onView(withId(R.id.buttonChangeColor)).perform(click());
            Thread.sleep(500);
            onView(withId(R.id.textView)).check(matches(withText(String.format("%d times clicked!", i + 1))));
        }
    }
}