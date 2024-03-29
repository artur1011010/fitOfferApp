package pl.artur.zaczek.fitOfferApp.rest.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseApiError {

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    public BadRequestException(String message, String code, String details) {
        super(message, code, details);
    }
}
