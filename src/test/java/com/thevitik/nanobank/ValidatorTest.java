package com.thevitik.nanobank;

import com.thevitik.nanobank.service.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class ValidatorTest {

    Validator validator;

    public ValidatorTest() {
        validator = mock(Validator.class, Answers.CALLS_REAL_METHODS);
    }

    @Test
    public void testIntegerNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validate("Text","Integer").integer();
        });
        assertEquals("It is not a number", exception.getMessage());
    }

    @Test
    public void testIntegerPositive() {
        assertDoesNotThrow(() -> {
            validator.validate("55","Integer").integer();
        });
    }

    @Test
    public void testPhoneNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validate("+382930184923","Phone").phone();
        });
        assertEquals("Incorrect phone format", exception.getMessage());
    }

    @Test
    public void testPhonePositive() {
        assertDoesNotThrow(() -> {
            validator.validate("0975550033","Phone").phone();
        });
    }

    @Test
    public void testPasswordNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            validator.validate("hello2023","Password").password();
        });
        assertEquals("Password is not secure", exception.getMessage());
    }

    @Test
    public void testPasswordPositive() {
        assertDoesNotThrow(() -> {
            validator.validate("1HelloWorld!","Password").password();
        });
    }
}
