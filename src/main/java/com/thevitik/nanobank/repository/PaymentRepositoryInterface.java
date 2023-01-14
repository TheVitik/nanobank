package com.thevitik.nanobank.repository;

import com.thevitik.nanobank.model.Payment;
import com.thevitik.nanobank.model.User;

import java.sql.SQLException;
import java.util.List;

public interface PaymentRepositoryInterface {
    int create(Payment payment) throws SQLException;

    void delete(int id) throws SQLException;

    Payment findById(int id) throws SQLException;

    List<Payment> getUserPayments(User user) throws SQLException;

    List<Payment> getUserPayments(User user, String orderBy, String orderType) throws SQLException;

    void update(Payment payment) throws SQLException;
}
