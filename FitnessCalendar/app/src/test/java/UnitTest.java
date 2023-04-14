import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test
 *
 */
public class UnitTest {

    @Test
    public void testAddition() {
        // illegal addition test
        int result = 2 + 3;
        assertEquals(5, result);
    }

    @Test
    public void testEmptyUsernameReturnsFalse() {
        boolean result = RegistrationUtil.validateRegistrationInput("", "123", "123");
        assertEquals(result, false);
    }
}