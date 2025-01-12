package com.taip.FillTheVoid.user;

import com.taip.FillTheVoid.gallery.Gallery;
import com.taip.FillTheVoid.painting.Painting;
import jakarta.persistence.Entity;

@Entity
public class Visitor extends User {
    public Visitor() {
        super();
    }

    public Visitor(String firstName, String lastName, String email, String password, Role role) {
        super(firstName, lastName, email, password, role);
    }

    public void visitGallery(Gallery gallery) {
//        TODO
        System.out.println("Visiting gallery: " + gallery.getGalleryName());
    }

    public void visitPainting(Painting painting) {
//        TODO
        System.out.println("Visiting painting: " + painting.getPaintingName());
    }

    public void addComment(Painting painting, String comment) {
//        TODO
        System.out.println("Added comment on painting: " + comment);
    }
}