package model.exception;

public class ShelfNotFoundException extends RuntimeException {
    public ShelfNotFoundException(String message) {
        super(message);
    }
}
