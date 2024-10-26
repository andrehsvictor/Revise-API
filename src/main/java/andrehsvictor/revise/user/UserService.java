package andrehsvictor.revise.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import andrehsvictor.revise.exception.ReviseException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User existingUser, User newUser) {
        existingUser
                .setFirstName(newUser.getFirstName() != null ? newUser.getFirstName() : existingUser.getFirstName());
        existingUser.setLastName(newUser.getLastName() != null ? newUser.getLastName() : existingUser.getLastName());
        existingUser.setEmail(newUser.getEmail() != null ? newUser.getEmail() : existingUser.getEmail());
        existingUser.setUsername(newUser.getUsername() != null ? newUser.getUsername() : existingUser.getUsername());

        return userRepository.save(existingUser);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User findByOauthId(String oauthId) {
        List<String> errors = List.of("User not found with OAuth ID: " + oauthId);
        return userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new ReviseException(HttpStatus.NOT_FOUND, errors));
    }

    public User findByUsername(String username) {
        List<String> errors = List.of("User not found with username: " + username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ReviseException(HttpStatus.NOT_FOUND, errors));
    }

    public User getCurrentUser() {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();
        return findByOauthId(jwt.getName());
    }

    public User findByEmail(String email) {
        List<String> errors = List.of("User not found with e-mail: " + email);
        return userRepository.findByEmail(email).orElseThrow(() -> new ReviseException(HttpStatus.NOT_FOUND, errors));
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
