package com.thevitik.nanobank;

import com.thevitik.nanobank.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    User user;

    public UserTest() {
        user = new User().setId(1)
                .setFirstName("Stepan")
                .setLastName("Stepanenko")
                .setPhone("0987654321")
                .setEncryptedPassword("11111111")
                .setRole(User.USER);
    }

    @Test
    public void testHasValidPassword() {
        assertTrue(user.hasValidPassword("11111111"));
    }

    @Test
    public void testGetName() {
        assertEquals("Stepan Stepanenko", user.getName());
    }

    @Test
    public void testIsBanned() {
        assertFalse(user.isBanned());
    }

}
