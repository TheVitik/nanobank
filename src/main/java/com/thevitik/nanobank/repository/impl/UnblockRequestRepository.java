package com.thevitik.nanobank.repository.impl;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.UnblockRequest;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.UnblockRequestRepositoryInterface;
import com.thevitik.nanobank.service.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnblockRequestRepository implements UnblockRequestRepositoryInterface {
    @Override
    public int create(UnblockRequest request) throws SQLException {
        String query = "INSERT INTO unblock_requests VALUES(null,?)";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, request.getCard().getId());
            return ps.executeUpdate();
        }
    }

    @Override
    public void delete(UnblockRequest request) throws SQLException {
        String deleteQuery = "DELETE FROM unblock_requests WHERE id=?";
        String updateQuery = "UPDATE cards SET is_blocked=0 WHERE id=?";
        Connection con = Database.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement ps1 = con.prepareStatement(deleteQuery);
            ps1.setInt(1, request.getId());
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(updateQuery);
            ps2.setInt(1, request.getCard().getId());
            ps2.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw new SQLException(e.getMessage());
        } finally {
            con.close();
        }
    }

    /**
     * Find card by id
     */
    @Override
    public UnblockRequest findById(int id) throws SQLException {
        String query = "SELECT r.id as r_id, c.*, u.id as u_id, u.first_name, u.last_name, u.phone " +
                "FROM unblock_requests r INNER JOIN cards c ON r.card_id=c.id " +
                "INNER JOIN users u ON c.owner_id=u.id WHERE r.id=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUnblockRequest(rs);
            }
        }
        return null;
    }

    @Override
    public List<UnblockRequest> getAll() throws SQLException {
        String query = "SELECT r.id as r_id, c.*, u.id as u_id, u.first_name, u.last_name, u.phone " +
                "FROM unblock_requests r INNER JOIN cards c ON r.card_id=c.id " +
                "INNER JOIN users u ON c.owner_id=u.id";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            List<UnblockRequest> requests = new ArrayList<>();
            while (rs.next()) {
                requests.add(extractUnblockRequest(rs));
            }
            return requests;
        }
    }

    private UnblockRequest extractUnblockRequest(ResultSet rs) throws SQLException {
        User user = new User().setId(rs.getInt("u_id"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setPhone(rs.getString("phone"));

        Card card = new Card().setId(rs.getInt("id"))
                .setNumber(rs.getLong("number"))
                .setExpirationDate(rs.getDate("expiration_date"))
                .setCvv(rs.getInt("cvv"))
                .setBalance(rs.getInt("balance"))
                .setOwner(user)
                .setBlocked(rs.getBoolean("is_blocked"));

        return new UnblockRequest().setId(rs.getInt("r_id"))
                .setCard(card);
    }
}
