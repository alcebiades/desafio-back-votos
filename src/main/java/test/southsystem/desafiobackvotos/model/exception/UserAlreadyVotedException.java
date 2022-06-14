package test.southsystem.desafiobackvotos.model.exception;

public class UserAlreadyVotedException extends RuntimeException {

    public UserAlreadyVotedException(String message) {
        super(message);
    }
}
