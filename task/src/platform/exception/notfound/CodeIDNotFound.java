package platform.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CodeIDNotFound extends RuntimeException {

    public CodeIDNotFound(long codeID) {
        super(String.format("Error: Code ID: %d not found.", codeID));
    }
}
