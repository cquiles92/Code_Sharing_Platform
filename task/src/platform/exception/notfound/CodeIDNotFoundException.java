package platform.exception.notfound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CodeIDNotFoundException extends RuntimeException {

    public CodeIDNotFoundException(String codeID) {
        super(String.format("Error: Code ID: %s not found.", codeID));
    }
}
