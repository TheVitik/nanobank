package com.thevitik.nanobank.model;

import java.util.Date;

public class Payment {
    private Integer id = null;
    private Card sender;
    private Card receiver;
    private int balance;
    private int status;
    private Date sentDate;

    public static final int PREPARED = 1;
    public static final int SENT = 2;

    public Payment() {

    }

    public Integer getId() {
        return id;
    }

    public Payment setId(Integer id) {
        this.id = id;
        return this;
    }

    public Card getSender() {
        return sender;
    }

    public Payment setSender(Card sender) {
        this.sender = sender;
        return this;
    }

    public Card getReceiver() {
        return receiver;
    }

    public Payment setReceiver(Card receiver) {
        this.receiver = receiver;
        return this;
    }

    public int getBalance() {
        return balance;
    }

    public Payment setBalance(int balance) {
        this.balance = balance;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Payment setStatus(int status) {
        this.status = status;
        return this;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public Payment setSentDate(Date sentDate) {
        this.sentDate = sentDate;
        return this;
    }
}
