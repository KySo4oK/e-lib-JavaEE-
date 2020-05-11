package model.exception;

//@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class BookAlreadyExistException extends RuntimeException {
    public BookAlreadyExistException(String message) {
        super(message);
    }
}
