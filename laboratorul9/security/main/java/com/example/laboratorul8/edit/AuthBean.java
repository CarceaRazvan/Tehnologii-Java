package com.example.laboratorul8.edit;
import com.example.laboratorul8.filter.CacheFilter;

import com.example.laboratorul8.model.*;
import com.example.laboratorul8.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;


@Named
@SessionScoped
public class AuthBean implements Serializable {

    private static final String SECRET_KEY = "c76d8eedad5c4425f3d7faa8e5623f85b6b3f2ab2c0d4698868c578671d280e4"; // Înlocuiește cu o cheie secretă sigură

    @Getter
    @Setter
    private boolean loggedIn;

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String role;

    @Inject
    AuthService authService;

    public void login() {
        try {
            User user = authService.login(username, password);
            role = user.getUserRole().toString();
            CacheFilter.deleteCacheByKey(username, role);

            if (user != null) {
                String token = generateJwt(user);
                loggedIn = true;

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Login reușit!", "Bun venit, " + username));

                PrimeFaces.current().executeScript("localStorage.setItem('jwt_auth', '" + token + "');");
                PrimeFaces.current().ajax().update("menuBar");
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login eșuat", "Username sau parolă incorectă."));
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Eroare autentificare", "Autentificarea a eșuat. Detalii: " + e.getMessage()));
        }

    }

    public String generateJwt(User user) {
        long expirationTime = 86400000; // 1 zi în milisecunde

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("name", user.getName())
                .claim("role", user.getUserRole().toString())
                .claim("courses", user instanceof Teacher ?
                        ((Teacher) user).getCourses().stream()
                                .map(Course::getName)
                                .collect(Collectors.toList()) : null)
                .claim("year", user instanceof Student ?
                        ((Student) user).getYear() : null)
                .claim("semester", user instanceof Student ?
                        ((Student) user).getSemester() : null)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void logout() {

        CacheFilter.deleteCacheByKey(username, role);
        PrimeFaces.current().executeScript("localStorage.removeItem('jwt_auth');");
        this.loggedIn = false;

        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

            request.logout();
            externalContext.invalidateSession();
            externalContext.log("Logout: " + username);
        } catch (ServletException ex) {
            System.err.println(ex);
        }
    }

}
