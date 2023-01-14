package com.thevitik.nanobank.repository.impl;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.Payment;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.CardRepositoryInterface;
import com.thevitik.nanobank.repository.PaymentRepositoryInterface;
import com.thevitik.nanobank.service.utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRepository implements PaymentRepositoryInterface {

    CardRepositoryInterface repository;

    public PaymentRepository(CardRepositoryInterface repository) {
        this.repository = repository;
    }

    /**
     * Create a new payment
     */
    @Override
    public int create(Payment payment) throws SQLException {
        String queryCreate = "INSERT INTO payments VALUES(null,?,?,?,?,?)";
        String queryUpdate = "UPDATE cards SET balance=? WHERE id=?";

        int result = 0;
        Connection con = Database.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement ps1 = con.prepareStatement(queryCreate);
            ps1.setInt(1, payment.getSender().getId());
            ps1.setInt(2, payment.getReceiver().getId());
            ps1.setInt(3, payment.getBalance());
            ps1.setInt(4, payment.getStatus());
            ps1.setDate(5, new Date(payment.getSentDate().getTime()));


            result = ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(queryUpdate);
            ps2.setInt(1, payment.getSender().getBalance());
            ps2.setInt(2, payment.getSender().getId());
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(queryUpdate);
            ps3.setInt(1, payment.getReceiver().getBalance());
            ps3.setInt(2, payment.getReceiver().getId());
            ps3.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        } finally {
            con.close();
        }
        return result;
    }

    /**
     * Delete a payment
     */
    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM payments WHERE id=?";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Find card by id
     */
    @Override
    public Payment findById(int id) throws SQLException {
        String query = "SELECT p.id, p.balance, p.status, p.sent_date, s.number as snum, r.number as rnum, " +
                "s.balance as sbal, r.balance as rbal, s.id as sid, r.id as rid," +
                " su.first_name as sfname, su.last_name as slname, ru.first_name as rfname, ru.last_name as rlname, " +
                "su.phone as sphone, ru.phone as rphone " +
                "FROM payments p INNER JOIN cards s ON p.sender_id=s.id INNER JOIN cards r ON p.receiver_id=r.id " +
                "INNER JOIN users su ON s.owner_id=su.id INNER JOIN users ru ON r.owner_id=ru.id WHERE p.id=?;";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractPayment(rs);
            }
        }
        return null;
    }

    @Override
    public List<Payment> getUserPayments(User user) throws SQLException {
        String query = "SELECT p.id, p.balance, p.status, p.sent_date, s.number as snum, r.number as rnum, " +
                "s.balance as sbal, r.balance as rbal, s.id as sid, r.id as rid," +
                " su.first_name as sfname, su.last_name as slname, ru.first_name as rfname, ru.last_name as rlname, " +
                "su.phone as sphone, ru.phone as rphone " +
                "FROM payments p INNER JOIN cards s ON p.sender_id=s.id INNER JOIN cards r ON p.receiver_id=r.id " +
                "INNER JOIN users su ON s.owner_id=su.id INNER JOIN users ru ON r.owner_id=ru.id WHERE su.id=? " +
                "ORDER BY p.id DESC";
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            List<Payment> payments = new ArrayList<>();
            while (rs.next()) {
                payments.add(extractPayment(rs));
            }
            return payments;
        }
    }

    @Override
    public List<Payment> getUserPayments(User user, String orderBy, String orderType) throws SQLException {
        String query = "SELECT p.id, p.balance, p.status, p.sent_date, s.number as snum, r.number as rnum, " +
                "s.balance as sbal, r.balance as rbal, s.id as sid, r.id as rid," +
                " su.first_name as sfname, su.last_name as slname, ru.first_name as rfname, ru.last_name as rlname, " +
                "su.phone as sphone, ru.phone as rphone " +
                "FROM payments p INNER JOIN cards s ON p.sender_id=s.id INNER JOIN cards r ON p.receiver_id=r.id " +
                "INNER JOIN users su ON s.owner_id=su.id INNER JOIN users ru ON r.owner_id=ru.id WHERE su.id=? " +
                "ORDER BY " + orderBy + " " + orderType;
        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();
            List<Payment> payments = new ArrayList<>();
            while (rs.next()) {
                payments.add(extractPayment(rs));
            }
            return payments;
        }
    }

    /**
     * Update payment status only
     */
    @Override
    public void update(Payment payment) throws SQLException {
        String query = "UPDATE payments SET status=? WHERE id=?";
        String queryUpdate = "UPDATE cards SET balance=? WHERE id=?";
        Connection con = Database.getConnection();
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, payment.getStatus());
            ps.setInt(2, payment.getId());
            ps.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement(queryUpdate);
            ps2.setInt(1, payment.getSender().getBalance());
            ps2.setInt(2, payment.getSender().getId());
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(queryUpdate);
            ps3.setInt(1, payment.getReceiver().getBalance());
            ps3.setInt(2, payment.getReceiver().getId());
            ps3.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        } finally {
            con.close();
        }
    }

    private Payment extractPayment(ResultSet rs) throws SQLException {
        User senderUser = new User().setFirstName(rs.getString("sfname"))
                .setLastName(rs.getString("slname"))
                .setPhone(rs.getString("sphone"));
        User receiverUser = new User().setFirstName(rs.getString("sfname"))
                .setLastName(rs.getString("slname"))
                .setPhone(rs.getString("rphone"));

        Card senderCard = new Card().setId(rs.getInt("sid"))
                .setNumber(rs.getLong("snum"))
                .setOwner(senderUser)
                .setBalance(rs.getInt("sbal"));
        Card receiverCard = new Card().setId(rs.getInt("rid"))
                .setNumber(rs.getLong("rnum"))
                .setOwner(receiverUser)
                .setBalance(rs.getInt("rbal"));

        return new Payment().setId(rs.getInt("id"))
                .setSender(senderCard)
                .setReceiver(receiverCard)
                .setBalance(rs.getInt("balance"))
                .setStatus(rs.getInt("status"))
                .setSentDate(rs.getDate("sent_date"));
    }
}
