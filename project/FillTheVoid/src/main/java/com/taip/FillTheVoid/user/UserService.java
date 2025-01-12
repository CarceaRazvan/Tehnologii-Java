package com.taip.FillTheVoid.user;

import com.taip.FillTheVoid.user.Owner.Owner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User getUserByEmail(String email) {


        Optional<User> user = repository.findByEmail(email);

        if (user.isEmpty()) {
            throw new IllegalStateException("Utilizatorul nu există cu acest email: "+email);
        }

        return user.get();
    }

}
