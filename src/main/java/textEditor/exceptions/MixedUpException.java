package textEditor.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MixedUpException extends Exception {

    public MixedUpException(String errorMessage) {
        super(errorMessage);
        log.error(errorMessage);
    }
}
