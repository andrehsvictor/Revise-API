package andrehsvictor.revise.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.revise.exception.ReviseException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserValidator {

    private final UserService userService;

    public void validate(User user) {
        List<String> errors = List.of();
        if (userService.existsByUsername(user.getUsername())) {
            errors.add("Username already in use: " + user.getUsername());
        }
        if (userService.existsByEmail(user.getEmail())) {
            errors.add("E-mail already in use: " + user.getEmail());
        }
        if (!errors.isEmpty()) {
            throw new ReviseException(HttpStatus.BAD_REQUEST, errors);
        }
    }
}
