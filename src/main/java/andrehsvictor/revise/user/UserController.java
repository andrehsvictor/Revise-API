package andrehsvictor.revise.user;

import java.util.Map;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import andrehsvictor.revise.keycloak.KeycloakUserService;
import andrehsvictor.revise.user.dto.request.UserRequestDTO;
import andrehsvictor.revise.user.dto.response.UserResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
    public UserResponseDTO create(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        User user = userRequestDTO.toUser();
        userValidator.validate(user);
        UserRepresentation kcUser = kcUserService.create(user, userRequestDTO.getPassword());
        user.setOauthId(kcUser.getId());
        user = userService.save(user);
        return new UserResponseDTO(user, kcUser);
    }

    @PutMapping("/api/v1/users/me")
    public UserResponseDTO update(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        User user = userService.getCurrentUser();
        User newUser = userRequestDTO.toUser();
        userValidator.validate(newUser);
        UserRepresentation kcUser = kcUserService.findById(user.getOauthId()).toRepresentation();
        kcUser = kcUserService.update(newUser, kcUser);
        user = userService.update(user, newUser);
        return new UserResponseDTO(user, kcUser);
    }

    @PutMapping("/api/v1/users/password")
    public Map<String, String> sendUpdatePasswordEmail(@RequestBody @Valid @Email String email) {
        User user = userService.findByEmail(email);
        kcUserService.sendUpdatePasswordEmail(user.getOauthId());
        return Map.of("message", "E-mail sent with instructions to update password");
    }

    @PutMapping("/api/v1/users/verify-email")
    public Map<String, String> sendVerifyEmail(@RequestBody @Valid @Email String email) {
        User user = userService.findByEmail(email);
        kcUserService.sendVerifyEmail(user.getOauthId());
        return Map.of("message", "E-mail sent with instructions to verify e-mail");
    }

}
