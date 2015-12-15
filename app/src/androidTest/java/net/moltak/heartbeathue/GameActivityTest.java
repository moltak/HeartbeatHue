package net.moltak.heartbeathue;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import net.moltak.heartbeathue.logic.LevelCreator;
import net.moltak.heartbeathue.logic.color.StageModeColorCreator;
import net.moltak.heartbeathue.logic.color.PartialColorBlindnessCreator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;

/**
 * Created by engeng on 12/1/15.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class GameActivityTest {

    @Rule
    public ActivityTestRule<GameActivity> mActivityRule = new ActivityTestRule<>(GameActivity.class, false, false);

//    @Test
//    public void buttonClickTest() {
//        onView(withId(R.id.buttonChangeColor)).perform(click());
//        onView(withId(R.id.textView)).check(matches(withText("1 times clicked!")));
//    }
//
//    @Test
//    public void button20TimesClickTest() throws InterruptedException {
//        for (int i = 0; i < 20; i ++) {
//            onView(withId(R.id.buttonChangeColor)).perform(click());
//            Thread.sleep(500);
//            onView(withId(R.id.textView)).check(matches(withText(String.format("%d times clicked!", i + 1))));
//        }
//    }

    @Test
    public void cieOppositeXyColorCreatorInitTest() {
        Intent i = new Intent();
        i.putExtra("mode", 0);

        GameActivity activity = mActivityRule.launchActivity(i);
        LevelCreator levelCreator = activity.getLevelCreator();
        assertThat(levelCreator.getSpecialColorCreator(), instanceOf(StageModeColorCreator.class));
    }

    @Test
    public void partialColorBlindnessCreatorInitTest() {
        Intent i = new Intent();
        i.putExtra("mode", 1);

        GameActivity activity = mActivityRule.launchActivity(i);
        LevelCreator levelCreator = activity.getLevelCreator();
        assertThat(levelCreator.getSpecialColorCreator(), instanceOf(PartialColorBlindnessCreator.class));
    }
}