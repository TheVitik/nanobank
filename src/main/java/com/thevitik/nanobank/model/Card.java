package com.thevitik.nanobank.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Card {
    private Integer id = null;
    private long number;
    private Date expirationDate;
    private Integer cvv;
    private Integer balance;
    private User owner;
    private boolean isBlocked;

    public Card() {

    }

    public Integer getId() {
        return id;
    }

    public Card setId(Integer id) {
        this.id = id;
        return this;
    }

    public long getNumber() {
        return number;
    }

    public String getFNumber() {
        String number = String.valueOf(this.number);
        List<String> parts = new ArrayList<>();
        for (int i = 0; i < 16; i += 4) {
            parts.add(number.substring(i, Math.min(16, i + 4)));
        }
        return String.join(" ", parts);
    }

    public Card setNumber(long number) {
        this.number = number;
        return this;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Card setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public String getFDate() {
        return new SimpleDateFormat("MM/yy").format(expirationDate);
    }

    public int getCvv() {
        return cvv;
    }

    public Card setCvv(int cvv) {
        this.cvv = cvv;
        return this;
    }

    public Integer getBalance() {
        return balance;
    }

    /**
     * Return formatted balance like 59.88
     */
    public Float getFBalance() {
        return (float) (balance / 100);
    }

    public Card setBalance(Integer balance) {
        this.balance = balance;
        return this;
    }

    public Card addBalance(int money) {
        this.balance += money * 100;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Card setOwner(User owner) {
        this.owner = owner;
        return this;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public Card setBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
        return this;
    }

    public Card block() {
        this.isBlocked = true;
        return this;
    }

    public Card unBlock() {
        this.isBlocked = false;
        return this;
    }
}
