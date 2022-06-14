package test.southsystem.desafiobackvotos.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.southsystem.desafiobackvotos.feign.CpfFeign;
import test.southsystem.desafiobackvotos.model.dto.CpfDTO;

@Log4j2
@RestController
@RequestMapping("/cpf")
public class CpfRest {

    private final CpfFeign cpfFeign;

    public CpfRest(CpfFeign cpfFeign) {
        this.cpfFeign = cpfFeign;
    }

    @GetMapping("/{cpf}")
    public CpfDTO post(@PathVariable("cpf") final String cpf) {
        log.info("I=Iniciando a consulta do usuario pelo CPF: " + cpf);
        return cpfFeign.getUser(cpf);
    }
}
