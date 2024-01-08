package pl.artur.zaczek.fitOfferApp.service;

import pl.artur.zaczek.fitOfferApp.rest.model.OfferDto;

import java.util.List;

public interface OfferService {
    List<OfferDto> getAll(boolean active, boolean current);

    void addOfferByUser(String token, OfferDto dto);

    void addTestOffer();

    List<OfferDto> getOffersByToken(String token);

    void deleteOfferById(String token, long trainingId);

    void signupOffer(String token, long trainingId);

    void resignOffer(String token, long trainingId);

    List<OfferDto> getClientOffersByToken(String token);
}
