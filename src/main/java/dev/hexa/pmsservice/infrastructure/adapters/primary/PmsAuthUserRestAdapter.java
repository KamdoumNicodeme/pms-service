package dev.hexa.pmsservice.infrastructure.adapters.primary;

import dev.hexa.pmsservice.application.dto.*;
import dev.hexa.pmsservice.infrastructure.config.security.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@CrossOrigin("*")
@RequestMapping("/pms/v1")
@RequiredArgsConstructor
public class PmsAuthUserRestAdapter {

    private final JwtTokenService jwtTokenService;
    private final List<UserDto> users = new CopyOnWriteArrayList<>();

    @PostMapping("/auth/authenticate")
    public AuthResponseDto authenticate(@RequestBody AuthRequestDto payload) {
        String login = payload == null || payload.login() == null ? "user@pms.local" : payload.login();
        List<String> roles = resolveRoles(login);
        String token = jwtTokenService.generateToken(login, roles);
        return new AuthResponseDto(token, login, roles);
    }

    @GetMapping("/user/all")
    public List<UserDto> getAllUsers() {
        return new ArrayList<>(users);
    }

    @PostMapping("/user/create")
    public UserCreateResponseDto createUser(@RequestBody UserDto user) {
        users.add(user);
        return new UserCreateResponseDto("created", user);
    }

    @DeleteMapping("/user/delete/{email}")
    public UserDeleteResponseDto deleteUser(@PathVariable String email) {
        users.removeIf(u -> email.equalsIgnoreCase(u.userEmail()));
        return new UserDeleteResponseDto("deleted", email);
    }

    @GetMapping("/user/{email}")
    public UserDto findUser(@PathVariable String email) {
        return users.stream()
                .filter(u -> email.equalsIgnoreCase(u.userEmail()))
                .findFirst()
                .orElse(new UserDto(
                        email,
                        email,
                        null,
                        List.of(new RoleDto("METIER")),
                        "not-found"
                ));
    }

    private List<String> resolveRoles(String login) {
        return users.stream()
                .filter(u -> login.equalsIgnoreCase(u.userEmail()))
                .findFirst()
                .map(u -> {
                    if (u.roles() == null || u.roles().isEmpty()) {
                        return List.of("METIER");
                    }
                    List<String> resolved = u.roles().stream().map(RoleDto::roleName).toList();
                    if (resolved.isEmpty()) {
                        return List.of("METIER");
                    }
                    return resolved;
                })
                .orElseGet(() -> login.toLowerCase().contains("admin") ? List.of("ADMIN") : List.of("METIER"));
    }
}
