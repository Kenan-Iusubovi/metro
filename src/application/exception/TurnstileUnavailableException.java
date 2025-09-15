package application.exception;

public class TurnstileUnavailableException extends RuntimeException {

        public TurnstileUnavailableException(String message) {
            super(message);
        }

        public TurnstileUnavailableException(String message, Throwable cause) {
            super(message, cause);
        }
    }

