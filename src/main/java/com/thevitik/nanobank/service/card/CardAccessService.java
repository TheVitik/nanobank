package com.thevitik.nanobank.service.card;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.model.UnblockRequest;
import com.thevitik.nanobank.model.User;
import com.thevitik.nanobank.repository.CardRepositoryInterface;
import com.thevitik.nanobank.repository.UnblockRequestRepositoryInterface;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.validation.card.CardAccessValidator;
import com.thevitik.nanobank.service.validation.card.UnblockValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class CardAccessService {
    private CardRepositoryInterface cardRepository;
    private UnblockRequestRepositoryInterface requestRepository;
    private AuthService auth;

    public CardAccessService(
            CardRepositoryInterface cardRepository,
            UnblockRequestRepositoryInterface requestRepository,
            AuthService auth
    ) {
        this.cardRepository = cardRepository;
        this.requestRepository = requestRepository;
        this.auth = auth;
    }

    public void update(HttpServletRequest request, CardAccessValidator validator) throws SQLException, IllegalAccessException {
        validator.validated(request);
        Card card = cardRepository.findById(Integer.parseInt(request.getParameter("card_id")));
        boolean action = getAction(request);
        validator.validateCard(card, auth.getUser(), action);
        if (!action && auth.getUser().getRole() == User.USER) {
            UnblockRequest ur = new UnblockRequest().setCard(card);
            requestRepository.create(ur);
            throw new SQLException("Unblock request was sent!");
        } else {
            card.setBlocked(getAction(request));
            cardRepository.update(card);
        }
    }

    public void confirm(HttpServletRequest request, UnblockValidator validator) throws SQLException, IllegalAccessException {
        validator.validated(request);
        UnblockRequest ur = requestRepository.findById(Integer.parseInt(request.getParameter("request_id")));
        validator.validateRequest(ur, auth.getUser());
        requestRepository.delete(ur);
    }

    private boolean getAction(HttpServletRequest request) {
        return request.getParameter("unblock") == null;
    }
}
