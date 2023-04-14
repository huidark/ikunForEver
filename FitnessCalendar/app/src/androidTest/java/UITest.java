import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.platform.app.InstrumentationRegistry;
import static org.junit.Assert.*;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;


import com.example.fitnesscalendar.R;
import com.example.fitnesscalendar.ui.activity.BarChartActivity;
import com.example.fitnesscalendar.ui.activity.CalenderActivity;

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
        ActivityScenario.launch(BarChartActivity.class);

        // Click on a button with the ID "myButton"
        Espresso.onView(ViewMatchers.withId(R.id.back_button))
                .perform(ViewActions.click());

    }
}