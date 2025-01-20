package exceptions;

public class FileDoesNotExistException extends MyException {
    public FileDoesNotExistException(String message) {
        super(message);
    }
}
