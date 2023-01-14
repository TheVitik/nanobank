package com.thevitik.nanobank.model;

public class UnblockRequest {

    private Integer id;
    private Card card;

    public UnblockRequest() {

    }

    public Integer getId() {
        return id;
    }

    public UnblockRequest setId(Integer id) {
        this.id = id;
        return this;
    }

    public Card getCard() {
        return card;
    }

    public UnblockRequest setCard(Card card) {
        this.card = card;
        return this;
    }
}
