package test.southsystem.desafiobackvotos.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.southsystem.desafiobackvotos.repository.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;
    @NotBlank(message = "O campo name e obrigatorio")
    private String name;
    @NotBlank(message = "O campo CPF e obrigatorio")
    private String cpf;

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .cpf(cpf)
                .build();
    }
}
