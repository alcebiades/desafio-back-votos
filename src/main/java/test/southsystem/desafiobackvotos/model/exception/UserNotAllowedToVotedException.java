package test.southsystem.desafiobackvotos.model.exception;

public class UserNotAllowedToVotedException extends RuntimeException {

    public UserNotAllowedToVotedException() {
        super("O usuario nao esta habilitado para votar");
    }
}
