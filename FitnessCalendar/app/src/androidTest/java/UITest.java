import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import static org.junit.Assert.*;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;


import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.model.User;
import com.example.fitnesscalendar.ui.activity.BarChartActivity;
import com.example.fitnesscalendar.ui.activity.CalenderActivity;
import com.example.fitnesscalendar.ui.activity.ProfileActivity;
import com.example.fitnesscalendar.ui.fragment.LoginFragment;

import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.Test;

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
        // Launch the MainActivity
        ActivityScenario<BarChartActivity> activityScenario = ActivityScenario.launch(BarChartActivity.class);
        // Click on a button with the ID "back button"
        onView(withId(R.id.back_button))
                .perform(click());
        // Close the activity
        activityScenario.close();
    }

    @Test
    public void testBarChartDisplayed() {
        // Launch the BarChartActivity
        ActivityScenario<BarChartActivity> activityScenario = ActivityScenario.launch(BarChartActivity.class);

        // Check if the BarChart is displayed on the screen
        Espresso.onView(ViewMatchers.withId(R.id.barchart))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        // Close the activity
        activityScenario.close();}
}