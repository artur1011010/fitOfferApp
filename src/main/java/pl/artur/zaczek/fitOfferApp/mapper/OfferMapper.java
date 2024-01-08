package pl.artur.zaczek.fitOfferApp.mapper;

import org.springframework.stereotype.Component;
import pl.artur.zaczek.fitOfferApp.jpa.entity.Offer;
import pl.artur.zaczek.fitOfferApp.rest.model.OfferDto;

@Component
public class OfferMapper {
    public OfferDto offerToOfferDto(final Offer entity){
        return OfferDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .isActive(entity.isActive())
                .address(entity.getAddress())
                .clientEmail(entity.getClientEmail())
                .ownerEmail(entity.getOwnerEmail())
                .duration(entity.getDuration())
                .startTime(entity.getStartTime())
                .photo(entity.getPhoto())
                .build();
    }
    public Offer offerDtoToOffer(final OfferDto dto){
        return Offer.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .active(dto.isActive())
                .address(dto.getAddress())
                .clientEmail(dto.getClientEmail())
                .ownerEmail(dto.getOwnerEmail())
                .duration(dto.getDuration())
                .startTime(dto.getStartTime())
                .photo(dto.getPhoto())
                .build();
    }
}
