package ee.selge.cicdwithjava.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserRepository repo;

  public UserController(UserRepository repo) {
    this.repo = repo;
  }

  @GetMapping
  public List<User> list() {
    return repo.findAll();
  }

  @PostMapping
  public ResponseEntity<User> create(@Valid @RequestBody User user) {
    if (repo.existsByEmail(user.getEmail())) {
      return ResponseEntity.badRequest().build();
    }
    User saved = repo.save(user);
    return ResponseEntity
        .created(URI.create("/api/users/" + saved.getId()))
        .body(saved);
  }
}
