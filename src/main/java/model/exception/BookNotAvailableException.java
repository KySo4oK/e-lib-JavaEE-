package model.exception;

//@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String message) {
        super(message);
    }
}
