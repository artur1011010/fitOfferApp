package pl.artur.zaczek.fitOfferApp.rest.model.auth;

import pl.artur.zaczek.fitOfferApp.utils.model.Role;

public record AuthorizationDto(String email, Role role) {
}
