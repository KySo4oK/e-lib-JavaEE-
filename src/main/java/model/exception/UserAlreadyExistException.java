package model.exception;

//@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
