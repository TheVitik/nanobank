package com.thevitik.nanobank;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.User;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    Card card;

    public CardTest() {
        User user = new User().setId(1)
                .setFirstName("Stepan")
                .setLastName("Stepanenko")
                .setPhone("0987654321")
                .setEncryptedPassword("11111111")
                .setRole(User.USER);

        card = new Card().setId(1)
                .setOwner(user)
                .setBalance(1000)
                .setCvv(444)
                .setNumber(9333914820385844L)
                .setExpirationDate(new Date())
                .setBlocked(true);
    }

    @Test
    public void testAddBalance() {
        assertEquals(1500, card.addBalance(5).getBalance());
    }

    @Test
    public void testGetFDate() {
        assertEquals("01/23", card.getFDate());
    }

    @Test
    public void testGetFNumber() {
        assertEquals("9333 9148 2038 5844", card.getFNumber());
    }

    @Test
    public void testgetFBalance() {
        assertEquals(Float.valueOf(10), card.getFBalance());
    }


    @Test
    public void testIsBlocked() {
        assertTrue(card.isBlocked());
    }

}
