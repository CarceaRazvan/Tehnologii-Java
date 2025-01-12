package com.taip.FillTheVoid.user.Owner;

import com.taip.FillTheVoid.gallery.Gallery;
import com.taip.FillTheVoid.painting.Painting;
import com.taip.FillTheVoid.user.Role;
import com.taip.FillTheVoid.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Owner extends User {

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Painting> paintings;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gallery> galleries;

    public Owner() {
        super();
    }

    public Owner(String firstName, String lastName, String email, String password, Role role, List<Painting> paintings, List<Gallery> galleries) {
        super(firstName, lastName, email, password, role);
        this.paintings = paintings;
        this.galleries = galleries;
    }

}