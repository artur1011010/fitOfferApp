package pl.artur.zaczek.fitOfferApp.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OfferDto {
    private Long id;
    private String title;
    private String description;
    private String address;
    private String ownerEmail;
    private String clientEmail;
    private boolean isActive;
    private int photo;
    private LocalDateTime startTime;
    private int duration;
}
