import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test
 *
 */
public class UnitTest {

    @Test
    public void testAddition() {
        // 测试加法操作
        int result = 2 + 3;
        assertEquals(5, result);
    }

    @Test
    public void testEmptyUsernameReturnsFalse() {
        boolean result = RegistrationUtil.validateRegistrationInput("", "123", "123");
        assertThat(result).isFalse();
    }
}