import android.content.Context;

import androidx.test.rule.ActivityTestRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.util.regex.Pattern.matches;

import com.example.fitnesscalendar.R;

/**
 * UITest
 *
 */
public class UITest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.fitnesscalendar", appContext.getPackageName());
    }

    @Test
    public void testButtonClick() {
//        onView(withId(R.id.button)).perform(click());
//
//        onView(withId(R.id.textView)).check(matches(withText("Fitness Calendar")));
    }

    @Test
    public void testEditTextInput() {
//        onView(withId(R.id.editText)).perform(typeText("Hello"));
//
//        onView(withId(R.id.editText)).check(matches(withText("Hello")));
    }
}