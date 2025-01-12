package com.taip.FillTheVoid.auth;

//import com.taip.FillTheVoid.config.JwtService;
import com.taip.FillTheVoid.config.proxy.JwtService;
import com.taip.FillTheVoid.user.Owner.Owner;
import com.taip.FillTheVoid.user.Owner.OwnerRepository;
import com.taip.FillTheVoid.user.Role;
import com.taip.FillTheVoid.user.User;
import com.taip.FillTheVoid.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {

        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        if (!isValidEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email-ul nu este valid");
        }

        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalStateException("Utilizatorul există deja cu acest email");
        }

//        repository.save(user);

        var owner = new Owner();
        owner.setFirstName(user.getFirstName());
        owner.setLastName(user.getLastName());
        owner.setEmail(user.getEmail());
        owner.setPassword(user.getPassword());
        owner.setRole(Role.USER);
        owner.setPaintings(new ArrayList<>());
        owner.setGalleries(new ArrayList<>());

        ownerRepository.save(owner);

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Parola sau email-ul sunt incorecte.");
        }


        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
