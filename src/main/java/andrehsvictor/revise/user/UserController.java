package andrehsvictor.revise.user;

import java.util.Map;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import andrehsvictor.revise.keycloak.KeycloakUserService;
import andrehsvictor.revise.user.dto.request.EmailRequestDTO;
import andrehsvictor.revise.user.dto.request.UpdateUserDTO;
import andrehsvictor.revise.user.dto.request.CreateUserDTO;
import andrehsvictor.revise.user.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KeycloakUserService kcUserService;
    private final UserValidator userValidator;

    @GetMapping("/api/v1/users/me")
    public UserResponseDTO me() {
        User user = userService.getCurrentUser();
        UserRepresentation kcUser = kcUserService.findById(user.getOauthId()).toRepresentation();
        return new UserResponseDTO(user, kcUser);
    }

    @PostMapping("/api/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO create(@RequestBody @Valid CreateUserDTO createUserDTO) {
        User user = createUserDTO.toUser();
        userValidator.validate(user);
        UserRepresentation kcUser = kcUserService.create(user, createUserDTO.getPassword());
        user.setOauthId(kcUser.getId());
        user = userService.save(user);
        return new UserResponseDTO(user, kcUser);
    }

    @PutMapping("/api/v1/users/me")
    public UserResponseDTO update(@RequestBody @Valid UpdateUserDTO updateUserDTO) {
        User user = userService.getCurrentUser();
        User newUser = updateUserDTO.toUser();
        userValidator.validate(newUser);
        UserRepresentation kcUser = kcUserService.findById(user.getOauthId()).toRepresentation();
        kcUser = kcUserService.update(newUser, kcUser);
        user = userService.update(user, newUser);
        return new UserResponseDTO(user, kcUser);
    }

    @PutMapping("/api/v1/users/password")
    public Map<String, String> sendUpdatePasswordEmail(@RequestBody @Valid EmailRequestDTO emailRequestDTO) {
        User user = userService.findByEmail(emailRequestDTO.getEmail());
        kcUserService.sendUpdatePasswordEmail(user.getOauthId());
        return Map.of("message", "E-mail sent with instructions to update password");
    }

    @PutMapping("/api/v1/users/verify-email")
    public Map<String, String> sendVerifyEmail(@RequestBody @Valid EmailRequestDTO emailRequestDTO) {
        User user = userService.findByEmail(emailRequestDTO.getEmail());
        kcUserService.sendVerifyEmail(user.getOauthId());
        return Map.of("message", "E-mail sent with instructions to verify e-mail");
    }

    @DeleteMapping("/api/v1/users/me")
    public ResponseEntity<Void> delete() {
        User user = userService.getCurrentUser();
        kcUserService.delete(user.getOauthId());
        userService.delete(user);
        return ResponseEntity.noContent().build();
    }

}
