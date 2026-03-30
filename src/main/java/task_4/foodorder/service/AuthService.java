package task_4.foodorder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import task_4.foodorder.entity.User;
import task_4.foodorder.repository.UserRepository;
import task_4.foodorder.security.JwtUtil; // ✅ IMPORTANT IMPORT

@Service
public class AuthService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil; // ✅ MUST BE INSIDE CLASS

    public User register(User user) {
        return repo.save(user);
    }

    public String login(User user) {
        User dbUser = repo.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(dbUser.getEmail());
    }
}