package com.example.courseregistration;
import com.example.courseregistration.Activity.SignUpActivity;

import org.junit.Test;
import static org.junit.Assert.*;
public class SignUpTest {
    @Test
    public void testValidNameReturnsTrue(){
        assertTrue(SignUpActivity.isValidName("Wasi"));
    }

    @Test
    public void testValidNameReturnsFalse(){
        assertFalse(SignUpActivity.isValidName("W4si"));
    }

    @Test
    public void testValidUserTypeTrue(){
        assertTrue(SignUpActivity.validUserType("instructor"));
    }

    @Test
    public void testValidUserTypeFalse(){
        assertFalse(SignUpActivity.validUserType("teacher"));
    }
}
