package exceptions;

import java.io.IOException;

public class FileDoesNotExistException extends MyException {
    public FileDoesNotExistException(String message) {
        super(message);
    }
}
