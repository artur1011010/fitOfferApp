package pl.artur.zaczek.fitOfferApp.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.artur.zaczek.fitOfferApp.jpa.entity.Offer;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllByOwnerEmail (String email);
    List<Offer> findAllByClientEmail (String email);
}
