package com.thevitik.nanobank.repository;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.UnblockRequest;
import com.thevitik.nanobank.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UnblockRequestRepositoryInterface {
    int create(UnblockRequest request) throws SQLException;

    void delete(UnblockRequest request) throws SQLException;

    UnblockRequest findById(int id) throws SQLException;

    List<UnblockRequest> getAll() throws SQLException;
}
