package test.southsystem.desafiobackvotos.rest;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.southsystem.desafiobackvotos.model.dto.AgendaDTO;
import test.southsystem.desafiobackvotos.service.AgendaService;

@RestController
@RequestMapping("/agendas")
public class AgendaRest {

    private final AgendaService agendaService;

    public AgendaRest(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @PostMapping
    public AgendaDTO post(@RequestBody @Valid final AgendaDTO agendaDTO) {
        return agendaService.createAgenda(agendaDTO);
    }

    @GetMapping
    public List<AgendaDTO> getAll() {
        return agendaService.getAllAgenda();
    }

    @GetMapping("/{id}")
    public AgendaDTO getById(@PathVariable("id") final Long id) {
        return agendaService.getById(id);
    }
}
