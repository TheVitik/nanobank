package com.thevitik.nanobank.service.card;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.CardRepositoryInterface;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class CreateCardService {

    private CardRepositoryInterface repository;
    private Random random;

    public CreateCardService(CardRepositoryInterface repository, Random random) {
        this.repository = repository;
        this.random = random;
    }

    public void create(User user) throws SQLException {
        Card card = new Card().setNumber(generateNumber())
                .setExpirationDate(generateDate(Calendar.getInstance()))
                .setCvv(generateCvv())
                .setBalance(0)
                .setOwner(user);
        repository.create(card);
    }

    /**
     * Generate random card number (even not valid)
     */
    private long generateNumber() {
        int firstPart = random.nextInt() % (99999999 - 10000000) + 99999999;
        int secondPart = random.nextInt() % (99999999 - 10000000) + 99999999;
        return Long.parseLong((firstPart + String.valueOf(secondPart)).substring(0, 16));
    }

    /**
     * Generate expiration date
     */
    private Date generateDate(Calendar calendar) {
        calendar.add(Calendar.YEAR, 10);
        return calendar.getTime();
    }

    /**
     * Generate random CVV code
     */
    private int generateCvv() {
        return random.nextInt(999);
    }
}
