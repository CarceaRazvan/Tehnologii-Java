package com.taip.FillTheVoid.badge;

import com.taip.FillTheVoid.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Badge {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private String type;
    @Column(name = "badge_value")
    private Integer value;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Badge() {}

    public Badge(String name, String description, String imageUrl, String type, Integer value, LocalDateTime createdAt, User user) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.type = type;
        this.value = value;
        this.createdAt = createdAt;
        this.user = user;
    }


    public void collectBadge() {
        System.out.println("Badge with ID " + id + " collected!");
    }
}
