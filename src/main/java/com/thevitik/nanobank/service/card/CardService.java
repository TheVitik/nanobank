package com.thevitik.nanobank.service.card;

import com.thevitik.nanobank.model.Card;
import com.thevitik.nanobank.repository.CardRepositoryInterface;
import com.thevitik.nanobank.service.auth.AuthService;
import com.thevitik.nanobank.service.validation.card.GetCardsValidator;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class CardService {

    CardRepositoryInterface cardRepository;
    AuthService auth;

    public CardService(CardRepositoryInterface cardRepository, AuthService auth) {
        this.cardRepository = cardRepository;
        this.auth = auth;
    }

    public List<Card> getCards(HttpServletRequest request, GetCardsValidator validator) throws SQLException {
        if (validator.shouldSort(request)) {
            String p = request.getParameter("sort");
            return cardRepository.getUserCards(auth.getUser(), getSortBy(p), getSortType(p));
        }
        return cardRepository.getUserCards(auth.getUser());
    }

    private String getSortBy(String param) {
        return param.split("-")[0];
    }

    private String getSortType(String param) {
        return param.split("-")[1];
    }
}
