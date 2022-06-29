package test.southsystem.desafiobackvotos.model.exception;

public class CpfInvalidException extends RuntimeException {

    public CpfInvalidException() {
        super("CPF invalido");
    }
}
