package andrehsvictor.revise.user.dto.response;

import java.time.LocalDateTime;

import org.keycloak.representations.idm.UserRepresentation;

import andrehsvictor.revise.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private boolean emailVerified;
    private String avatarUrl;
    private String oauthId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserResponseDTO(User user, UserRepresentation userRepresentation) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.emailVerified = userRepresentation.isEmailVerified();
        this.avatarUrl = user.getAvatarUrl();
        this.oauthId = user.getOauthId();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}