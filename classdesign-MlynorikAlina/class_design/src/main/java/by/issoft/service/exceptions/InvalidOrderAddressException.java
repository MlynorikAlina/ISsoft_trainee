package by.issoft.service.exceptions;

public class InvalidOrderAddressException extends RuntimeException {
    public InvalidOrderAddressException(String message) {
        super(message);
    }
}
