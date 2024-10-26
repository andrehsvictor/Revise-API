package andrehsvictor.revise.user.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailRequestDTO {

    @Email(message = "E-mail must be valid")
    private String email;
}
