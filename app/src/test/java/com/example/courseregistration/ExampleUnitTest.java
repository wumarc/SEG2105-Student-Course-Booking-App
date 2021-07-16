package com.example.courseregistration;
import com.example.courseregistration.Activity.SignUpActivity;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void testValidNameReturnsTrue(){
        assertTrue(SignUpActivity.isValidName("Wasi"));
    }

    @Test
    public void testValidNameReturnsFalse(){
        assertFalse(SignUpActivity.isValidName("W4si"));
    }

}