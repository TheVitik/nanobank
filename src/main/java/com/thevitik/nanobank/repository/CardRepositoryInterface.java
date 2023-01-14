package com.thevitik.nanobank.repository;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.User;

import java.sql.SQLException;
import java.util.List;

public interface CardRepositoryInterface {
    int create(Card card) throws SQLException;

    void delete(int id) throws SQLException;

    Card findFirst(User user) throws SQLException;

    Card findById(int id) throws SQLException;

    Card findByNumber(long number) throws SQLException;

    List<Card> getAll() throws SQLException;

    List<Card> getUserCards(User user) throws SQLException;

    List<Card> getUserCards(User user, String orderBy, String orderType) throws SQLException;

    void update(Card card) throws SQLException;
}
