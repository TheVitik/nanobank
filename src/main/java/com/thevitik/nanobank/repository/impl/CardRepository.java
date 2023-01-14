package com.thevitik.nanobank.repository.impl;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.CardRepositoryInterface;
import com.thevitik.nanobank.service.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardRepository implements CardRepositoryInterface {

    /**
     * Create a new card
     */
    @Override
    public int create(Card card) throws SQLException {
        String query = "INSERT INTO cards VALUES(null,?,?,?,?,?,?)";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, card.getNumber());
            ps.setDate(2, new Date(card.getExpirationDate().getTime()));
            ps.setInt(3, card.getCvv());
            ps.setInt(4, card.getBalance());
            ps.setInt(5, card.getOwner().getId());
            ps.setBoolean(6, card.isBlocked());
            return ps.executeUpdate();
        }
    }

    /**
     * Delete a card
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM cards WHERE id=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Find first user card
     */
    @Override
    public Card findFirst(User user) throws SQLException {
        String query = "SELECT c.*, u.id as u_id, u.first_name, u.last_name, u.phone FROM cards c " +
                "INNER JOIN users u ON c.owner_id=u.id WHERE u.id=? LIMIT 1";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractCard(rs);
            }
        }
        return null;
    }

    /**
     * Find card by id
     */
    @Override
    public Card findById(int id) throws SQLException {
        String query = "SELECT c.*, u.id as u_id, u.first_name, u.last_name, u.phone FROM cards c " +
                "INNER JOIN users u ON c.owner_id=u.id WHERE c.id=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractCard(rs);
            }
        }
        return null;
    }

    @Override
    public Card findByNumber(long number) throws SQLException {
        String query = "SELECT c.*, u.id as u_id, u.first_name, u.last_name, u.phone FROM cards c " +
                "INNER JOIN users u ON c.owner_id=u.id WHERE c.number=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, number);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractCard(rs);
            }
        }
        return null;
    }

    @Override
    public List<Card> getAll() throws SQLException {
        String query = "SELECT c.*, u.id as u_id, u.first_name, u.last_name, u.phone FROM cards c " +
                "INNER JOIN users u ON c.owner_id=u.id ORDER BY c.id DESC";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<Card> cards = new ArrayList<>();
            while (rs.next()) {
                cards.add(extractCard(rs));
            }
            return cards;
        }
    }

    @Override
    public List<Card> getUserCards(User user) throws SQLException {
        String query = "SELECT c.*, u.id as u_id, u.first_name, u.last_name, u.phone FROM cards c " +
                "INNER JOIN users u ON c.owner_id=u.id WHERE u.id=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            List<Card> cards = new ArrayList<>();
            while (rs.next()) {
                cards.add(extractCard(rs));
            }
            return cards;
        }
    }

    @Override
    public List<Card> getUserCards(User user, String orderBy, String orderType) throws SQLException {
        String query = "SELECT c.*, u.id as u_id, u.first_name, u.last_name, u.phone FROM cards c " +
                "INNER JOIN users u ON c.owner_id=u.id WHERE u.id=? ORDER BY " + orderBy + " " + orderType;
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            List<Card> cards = new ArrayList<>();
            while (rs.next()) {
                cards.add(extractCard(rs));
            }
            return cards;
        }
    }

    /**
     * Update card balance and status only
     */
    @Override
    public void update(Card card) throws SQLException {
        String query = "UPDATE cards SET balance=?, is_blocked=? WHERE id=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, card.getBalance());
            ps.setBoolean(2, card.isBlocked());
            ps.setInt(3, card.getId());
            ps.executeUpdate();
        }
    }

    private Card extractCard(ResultSet rs) throws SQLException {
        User user = new User().setId(rs.getInt("u_id"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setPhone(rs.getString("phone"));

        return new Card().setId(rs.getInt("id"))
                .setNumber(rs.getLong("number"))
                .setExpirationDate(rs.getDate("expiration_date"))
                .setCvv(rs.getInt("cvv"))
                .setBalance(rs.getInt("balance"))
                .setOwner(user)
                .setBlocked(rs.getBoolean("is_blocked"));
    }
}
