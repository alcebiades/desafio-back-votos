package test.southsystem.desafiobackvotos.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import test.southsystem.desafiobackvotos.feign.config.CpfFeignConfig;
import test.southsystem.desafiobackvotos.model.dto.CpfDTO;

@FeignClient(name = "CpfFeign",
        url = "https://user-info.herokuapp.com",
        configuration = CpfFeignConfig.class
)
public interface CpfFeign {

    @GetMapping(value = "/users/{cpf}")
    CpfDTO getUser(@PathVariable("cpf") final String cpf);
}
