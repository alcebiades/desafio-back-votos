package test.southsystem.desafiobackvotos.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import test.southsystem.desafiobackvotos.feign.CpfFeign;
import test.southsystem.desafiobackvotos.model.dto.CpfDTO;
import test.southsystem.desafiobackvotos.model.exception.CpfInvalidException;
import test.southsystem.desafiobackvotos.model.exception.NotFoundException;

@Log4j2
@Service
public class CpfService {

    private final CpfFeign cpfFeign;

    public CpfService(CpfFeign cpfFeign) {
        this.cpfFeign = cpfFeign;
    }

    public CpfDTO getCpf(final String cpf) {
        log.info("I=Iniciando a consulta do CPF do usuario, CPF={}", cpf);
        return cpfFeign.getUser(
                sanitizeCpf(cpf)
        );
    }

    public void validateCpf(final String cpf) {
        try {
            getCpf(
                    sanitizeCpf(cpf)
            );
        } catch (NotFoundException ex) {
            throw new CpfInvalidException();
        }
    }

    public String sanitizeCpf(final String cpf) {
        return cpf.replace(".", "").replace("-", "");
    }
}
