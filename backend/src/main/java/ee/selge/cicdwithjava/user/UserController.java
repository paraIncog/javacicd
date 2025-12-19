package ee.selge.cicdwithjava.user;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173") // Vue dev server
public class UserController {
  private final UserRepository repo;

  public UserController(UserRepository repo) {
    this.repo = repo;
  }

  @PostMapping
  public UserEntity create(@Valid @RequestBody UserEntity user) {
    user.setId(null); // ensure it's new
    return repo.save(user);
  }

  @GetMapping
  public List<UserEntity> list() {
    return repo.findAll();
  }
}
