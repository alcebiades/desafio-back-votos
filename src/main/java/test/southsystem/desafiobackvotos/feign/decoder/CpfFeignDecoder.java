package test.southsystem.desafiobackvotos.feign.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import test.southsystem.desafiobackvotos.model.exception.NotFoundException;

public class CpfFeignDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        if (response.status() == 404) {
            return new NotFoundException("Usuario nao encontrado na comunicacao com API externa!");
        }

        return new Exception("Erro generico de comunicacao com API externa");
    }
}
