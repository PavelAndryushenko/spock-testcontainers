package team.mediasoft.study.spocktestcontainers.exception;

public class BookNotAvailableException extends RuntimeException {

    public BookNotAvailableException(String message) {
        super(message);
    }
}
