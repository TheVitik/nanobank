package com.thevitik.nanobank.service.card;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.repository.CardRepositoryInterface;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.validation.card.DepositValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class CardBalanceService {
    private CardRepositoryInterface repository;
    private AuthService auth;

    public CardBalanceService(CardRepositoryInterface repository, AuthService auth) {
        this.repository = repository;
        this.auth = auth;
    }

    public void deposit(HttpServletRequest request, DepositValidator validator) throws SQLException {
        validator.validated(request);
        Card card = repository.findById(Integer.parseInt(request.getParameter("card_id")));
        validator.validateCard(card, auth.getUser());
        card.addBalance(Integer.parseInt(request.getParameter("balance")));
        repository.update(card);
    }
}
