package pl.artur.zaczek.fitOfferApp.rest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.artur.zaczek.fitOfferApp.rest.model.OfferDto;
import pl.artur.zaczek.fitOfferApp.service.OfferService;

import java.util.List;

@RestController
@RequestMapping("/offer")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class OfferController {

    private final OfferService offerService;

    @GetMapping("/all")
    public ResponseEntity<List<OfferDto>> getAll(@RequestParam final boolean active, @RequestParam final boolean current){
//        log.info("GET /offer/all");
        return ResponseEntity.ok(offerService.getAll(active, current));
    }


    @PostMapping
    public ResponseEntity<Void> addOfferByUser(@RequestBody final OfferDto dto, @RequestHeader(name = "Authorization") final String token){
        log.info("POST /offer:\n{}", dto);
        offerService.addOfferByUser(token, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<OfferDto>> getOffersByToken(@RequestHeader(name = "Authorization") final String token){
        log.info("GET /offer by token");
        final List<OfferDto> result = offerService.getOffersByToken(token);
        log.info("returning:\n{}", result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/client")
    public ResponseEntity<List<OfferDto>> getClientOffersByToken(@RequestHeader(name = "Authorization") final String token){
        log.info("GET /offer/client by token");
        final List<OfferDto> result = offerService.getClientOffersByToken(token);
        log.info("returning:\n{}", result);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/signup")
    public ResponseEntity<Void> signupOffer(@RequestParam final int trainingId, @RequestHeader(name = "Authorization") final String token){
        log.info("PATCH /offer/signup by token");
        offerService.signupOffer(token, trainingId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/resign")
    public ResponseEntity<Void> resignOffer(@RequestParam final int trainingId, @RequestHeader(name = "Authorization") final String token){
        log.info("PATCH /offer/resign by token");
        offerService.resignOffer(token, trainingId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteOfferById(@RequestParam final int trainingId, @RequestHeader(name = "Authorization") final String token){
        log.info("DELETE /offer?trainingId={}", trainingId);
        offerService.deleteOfferById(token, trainingId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addTestOffer(){
//        log.info("POST /offer/add");
        offerService.addTestOffer();
        return ResponseEntity.ok().build();
    }
}
