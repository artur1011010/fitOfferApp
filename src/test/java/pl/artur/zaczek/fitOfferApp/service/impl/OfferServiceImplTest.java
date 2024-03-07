package pl.artur.zaczek.fitOfferApp.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.artur.zaczek.fitOfferApp.jpa.entity.Offer;
import pl.artur.zaczek.fitOfferApp.jpa.repository.OfferRepository;
import pl.artur.zaczek.fitOfferApp.mapper.OfferMapper;
import pl.artur.zaczek.fitOfferApp.rest.model.OfferDto;
import pl.artur.zaczek.fitOfferApp.rest.model.auth.AuthorizationDto;
import pl.artur.zaczek.fitOfferApp.service.OfferService;
import pl.artur.zaczek.fitOfferApp.service.UserAuthClient;
import pl.artur.zaczek.fitOfferApp.utils.model.Role;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class OfferServiceImplTest {

    private OfferMapper offerMapper = new OfferMapper();

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private UserAuthClient userAuthClient;

    private OfferService offerService;

    private final String token = "dummyToken";

    private final String email = "user@example.com";
    @BeforeEach
    void setUp() {
        offerService = new OfferServiceImpl(offerRepository, offerMapper, userAuthClient);
        AuthorizationDto authorizationDto = new AuthorizationDto(email, Role.USER);
        when(userAuthClient.authorize(token)).thenReturn(authorizationDto);
    }

    @Test
    public void shouldReturnOffers (){
        when(offerRepository.findAll()).thenReturn(mockDbResponse());
        final List<OfferDto> all = offerService.getAll(false, false);
        final List<OfferDto> expected = mockDbResponse().stream()
                .map(entity -> offerMapper.offerToOfferDto(entity))
                .toList();
        Assertions.assertIterableEquals(all, expected);
    }

//    @Test
//    public void shouldReturnUserOffersByToken (){
//        offerService.getOffersByToken(token);
//        when(offerRepository.findAllByOwnerEmail())
//    }

    private List<Offer> mockDbResponse (){
        Offer build1 = Offer.builder()
                .photo(1)
                .active(true)
                .description("active1")
                .startTime(LocalDateTime.of(2024,2,13, 18,58,20))
                .title("active-title1")
                .duration(60)
                .ownerEmail("owner1@gmail.com")
                .address("address1")
                .build();
        Offer build2 = Offer.builder()
                .photo(1)
                .active(true)
                .description("active1")
                .startTime(LocalDateTime.of(2024,2,13, 18,58,20))
                .title("active-title1")
                .duration(60)
                .ownerEmail("owner1@gmail.com")
                .address("address1")
                .build();
        Offer build3 = Offer.builder()
                .photo(1)
                .active(false)
                .description("desc")
                .startTime(LocalDateTime.of(2024,2,13, 18,58,20))
                .title("title3")
                .duration(60)
                .ownerEmail("owner2@gmail.com")
                .address("address2")
                .build();
        return List.of(build1, build2, build3);
    }
}