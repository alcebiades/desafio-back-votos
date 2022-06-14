package test.southsystem.desafiobackvotos.rest;

import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.southsystem.desafiobackvotos.model.dto.VoteDTO;
import test.southsystem.desafiobackvotos.service.VoteService;

@RestController
@RequestMapping("/votes")
public class VoteRest {

    private final VoteService voteService;

    public VoteRest(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping
    public ResponseEntity<String> post(@RequestBody @Valid final VoteDTO user) {
        voteService.vote(user);
        return ResponseEntity.ok("Seu voto foi recebido com sucesso!");
    }
}
