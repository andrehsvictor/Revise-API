package andrehsvictor.revise.keycloak;

import java.util.List;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import andrehsvictor.revise.user.User;

@Service
public class UserRepresentationCreator {

    private final List<String> REALM_ROLES = List.of("user");
    private final List<String> REQUIRED_ACTIONS = List.of("VERIFY_EMAIL");
    
    public UserRepresentation create(User user, String password) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        userRepresentation.setFirstName(user.getFirstName());
        userRepresentation.setLastName(user.getLastName());
        userRepresentation.setEnabled(true);
        userRepresentation.setRealmRoles(REALM_ROLES);
        userRepresentation.setRequiredActions(REQUIRED_ACTIONS);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        
        return userRepresentation;
    }
}
