package andrehsvictor.revise.keycloak;

import java.util.List;

import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.revise.exception.ReviseException;
import andrehsvictor.revise.user.User;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {
    
    private final RealmResource keycloak;
    private final UserRepresentationCreator userRepresentationCreator;

    public UserRepresentation create(User user, String password) {
        UserRepresentation userRepresentation = userRepresentationCreator.create(user, password);
        
        Response response = keycloak.users().create(userRepresentation);
        String userId = CreatedResponseUtil.getCreatedId(response);
        response.close();

        return findById(userId).toRepresentation();
    }

    public UserResource findById(String id) {
        return keycloak.users().get(id);
    }

    public void sendUpdatePasswordEmail(String id) {
        findById(id).executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    public void sendVerifyEmail(String id) {
        UserResource userResource = findById(id);
        UserRepresentation userRepresentation = userResource.toRepresentation();
        if (userRepresentation.isEmailVerified()) {
            throw new ReviseException(HttpStatus.BAD_REQUEST, List.of("E-mail is already verified"));
        }
        userResource.sendVerifyEmail();
    }

    public UserRepresentation update(User user, UserRepresentation userRepresentation) {
        userRepresentation.setFirstName(user.getFirstName() != null ? user.getFirstName() : userRepresentation.getFirstName());
        userRepresentation.setLastName(user.getLastName() != null ? user.getLastName() : userRepresentation.getLastName());
        userRepresentation.setEmail(user.getEmail() != null ? user.getEmail() : userRepresentation.getEmail());
        userRepresentation.setUsername(user.getUsername() != null ? user.getUsername() : userRepresentation.getUsername());

        if (user.getEmail() != null && !user.getEmail().equals(userRepresentation.getEmail())) {
            userRepresentation.setEmailVerified(false);
        }

        keycloak.users().get(userRepresentation.getId()).update(userRepresentation);
        return findById(userRepresentation.getId()).toRepresentation();
    }

    public void delete(String id) {
        keycloak.users().get(id).remove();
    }
}
