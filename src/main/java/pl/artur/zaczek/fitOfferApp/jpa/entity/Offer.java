package pl.artur.zaczek.fitOfferApp.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(schema = "fit_offer_db", name = "OFFERS")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @Column(length = 8000)
    private String description;
    @NotNull
    private String address;
    @NotNull
    private String ownerEmail;
    private String clientEmail;
    private boolean active;
    private int photo;
//    @NotNull
    private LocalDateTime startTime;
    private int duration;
}
