package pl.artur.zaczek.fitOfferApp.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.artur.zaczek.fitOfferApp.jpa.entity.Offer;
import pl.artur.zaczek.fitOfferApp.jpa.repository.OfferRepository;
import pl.artur.zaczek.fitOfferApp.mapper.OfferMapper;
import pl.artur.zaczek.fitOfferApp.rest.error.BadRequestException;
import pl.artur.zaczek.fitOfferApp.rest.error.ForbiddenException;
import pl.artur.zaczek.fitOfferApp.rest.model.OfferDto;
import pl.artur.zaczek.fitOfferApp.service.OfferService;
import pl.artur.zaczek.fitOfferApp.service.UserAuthClient;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final OfferMapper offerMapper;
    private final UserAuthClient authClient;

    @Override
    public List<OfferDto> getAll(boolean active, boolean current) {
        if(current && active){
            return offerRepository.findAll().stream()
                    .filter(offer-> offer.getStartTime().isAfter(LocalDateTime.now()) && offer.isActive())
                    .map(offerMapper::offerToOfferDto)
                    .toList();
        }
        if(active){
            return offerRepository.findAll().stream()
                    .filter(Offer::isActive)
                    .map(offerMapper::offerToOfferDto)
                    .toList();
        }
        if(current){
            return offerRepository.findAll().stream()
                    .filter(offer-> offer.getStartTime().isAfter(LocalDateTime.now()))
                    .map(offerMapper::offerToOfferDto)
                    .toList();
        }
        return offerRepository.findAll().stream()
                .map(offerMapper::offerToOfferDto)
                .toList();
    }

    @Override
    @Transactional
    public void addOfferByUser(final String token, final OfferDto dto) {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        final Offer offer = offerMapper.offerDtoToOffer(dto);
        offer.setActive(true);
        offer.setOwnerEmail(email);
        log.info("oferta po mapowaniu: \n{}", offer);
        offerRepository.save(offer);
    }

    @Override
    public List<OfferDto> getOffersByToken(final String token) {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        return offerRepository.findAllByOwnerEmail(email).stream()
                .map(offerMapper::offerToOfferDto)
                .toList();
    }

    @Override
    public List<OfferDto> getClientOffersByToken(final String token) {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        return offerRepository.findAllByClientEmail(email).stream()
                .map(offerMapper::offerToOfferDto)
                .toList();
    }

    @Override
    public void deleteOfferById(final String token, final long trainingId) {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        final Optional<Offer> byId = offerRepository.findById(trainingId);
        if (byId.isPresent()) {
            final Offer offer = byId.get();
            if (offer.getOwnerEmail().equals(email)) {
                offerRepository.delete(offer);
            } else {
                throw new ForbiddenException("Offer id: " + trainingId + " has different owner");
            }
        } else {
            throw new BadRequestException("Offer id: " + trainingId + " not exist!");
        }
    }

    @Override
    @Transactional
    public void signupOffer(final String token, final long trainingId) {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        final Optional<Offer> byId = offerRepository.findById(trainingId);
        if (byId.isPresent()) {
            final Offer offer = byId.get();
            if (offer.isActive() && StringUtils.isBlank(offer.getClientEmail())) {
                offer.setClientEmail(email);
                offerRepository.save(offer);
            } else {
                throw new BadRequestException("Offer id: " + trainingId + " is not active or user can not sign up because offer is already taken");
            }
        } else {
            throw new BadRequestException("Offer id: " + trainingId + " not exist!");
        }
    }

    @Override
    @Transactional
    public void resignOffer(final String token, final long trainingId) {
        final String email = authClient.authorize(token).email();
        log.info("email: " + email);
        final Optional<Offer> byId = offerRepository.findById(trainingId);
        if (byId.isPresent()) {
            final Offer offer = byId.get();
            if (offer.isActive() && offer.getClientEmail().equals(email)) {
                offer.setClientEmail(null);
                offerRepository.save(offer);
            } else {
                throw new BadRequestException("Offer id: " + trainingId + " is not active or currently different user is signup");
            }
        } else {
            throw new BadRequestException("Offer id: " + trainingId + " not exist!");
        }
    }

    @Override
    public void addTestOffer() {
        final Offer offer = Offer.builder()
                .ownerEmail("akademia_plywania@ing.pl")
                .title("Time4Swim - więcej niż nauka pływania!")
                .description("W Akademii Pływania „Time4Swim” nie tylko uczymy techniki i stylów pływania, ale również skupiamy się na podstawach oraz oswajaniu z wodą. Rozumiemy, że dla wielu osób, zarówno dzieci jak i dorosłych, pierwszym krokiem jest przełamanie obaw i stworzenie pewności siebie w środowisku wodnym.")
                .startTime(LocalDateTime.of(2024, 3, 1, 18, 0))
                .active(true)
                .duration(50)
                .photo(7)
                .address("Zatoka sportu - aleje Politechniki 10, 93-590 Łódź")
                .build();

        final Offer offer2 = Offer.builder()
                .ownerEmail("akademia_plywania@ing.pl")
                .title("Time4Swim - więcej niż nauka pływania!")
                .description("W Akademii Pływania „Time4Swim” nie tylko uczymy techniki i stylów pływania, ale również skupiamy się na podstawach oraz oswajaniu z wodą. Rozumiemy, że dla wielu osób, zarówno dzieci jak i dorosłych, pierwszym krokiem jest przełamanie obaw i stworzenie pewności siebie w środowisku wodnym.")
                .startTime(LocalDateTime.of(2024, 3, 1, 19, 0))
                .active(true)
                .duration(50)
                .photo(7)
                .address("Zatoka sportu - aleje Politechniki 10, 93-590 Łódź")
                .build();

        final Offer offer3 = Offer.builder()
                .ownerEmail("akademia_plywania@ing.pl")
                .title("Time4Swim - więcej niż nauka pływania!")
                .description("W Akademii Pływania „Time4Swim” nie tylko uczymy techniki i stylów pływania, ale również skupiamy się na podstawach oraz oswajaniu z wodą. Rozumiemy, że dla wielu osób, zarówno dzieci jak i dorosłych, pierwszym krokiem jest przełamanie obaw i stworzenie pewności siebie w środowisku wodnym.")
                .startTime(LocalDateTime.of(2024, 3, 1, 20, 0))
                .active(true)
                .duration(50)
                .photo(7)
                .address("Zatoka sportu - aleje Politechniki 10, 93-590 Łódź")
                .build();

        final Offer offer4 = Offer.builder()
                .ownerEmail("kuznia@gmail.com")
                .title("KickBoxing")
                .description("Zajęcia dla kobiet i mężczyzn (konfrontacja stylów uderzanych i takich, jak KARATE, KICK BOXING, MUAY THAI). Zajęcia poświęcone są nauce stosowania technik bokserskich oraz nożnych. Praca na workach, łapach treningowych, tarczach, praca z partnerem.Zajęcia dla kobiet i mężczyzn (konfrontacja stylów uderzanych i takich, jak KARATE, KICK BOXING, MUAY THAI). Zajęcia poświęcone są nauce stosowania technik bokserskich oraz nożnych. Praca na workach, łapach treningowych, tarczach, praca z partnerem.")
                .startTime(LocalDateTime.of(2024, 3, 14, 16, 45))
                .active(true)
                .duration(75)
                .photo(6)
                .address("Kuźnia Centrum Atletyki - Gdańska 126/128, 90-520 Łódź")
                .build();

        final Offer offer5 = Offer.builder()
                .ownerEmail("kuznia@gmail.com")
                .title("KickBoxing")
                .description("Zajęcia dla kobiet i mężczyzn (konfrontacja stylów uderzanych i takich, jak KARATE, KICK BOXING, MUAY THAI). Zajęcia poświęcone są nauce stosowania technik bokserskich oraz nożnych. Praca na workach, łapach treningowych, tarczach, praca z partnerem.Zajęcia dla kobiet i mężczyzn (konfrontacja stylów uderzanych i takich, jak KARATE, KICK BOXING, MUAY THAI). Zajęcia poświęcone są nauce stosowania technik bokserskich oraz nożnych. Praca na workach, łapach treningowych, tarczach, praca z partnerem.")
                .startTime(LocalDateTime.of(2024, 3, 14, 16, 45))
                .active(true)
                .duration(75)
                .photo(6)
                .address("Kuźnia Centrum Atletyki - Gdańska 126/128, 90-520 Łódź")
                .build();

        offerRepository.saveAll(List.of(offer, offer2, offer3, offer4, offer5));
    }
}
