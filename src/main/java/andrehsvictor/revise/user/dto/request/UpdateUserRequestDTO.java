package andrehsvictor.revise.user.dto.request;

import andrehsvictor.revise.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequestDTO {
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    @Email(message = "E-mail must be valid")
    private String email;

    @Size(min = 2, max = 50, message = "Username must be between 2 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must contain only letters and numbers")
    private String username;

    private String avatarUrl;

    public User toUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);
        user.setAvatarUrl(avatarUrl);
        return user;
    }
}
