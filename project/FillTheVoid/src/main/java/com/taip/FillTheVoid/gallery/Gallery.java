package com.taip.FillTheVoid.gallery;

import com.taip.FillTheVoid.painting.Painting;
import com.taip.FillTheVoid.user.Owner.Owner;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"gallery_name", "owner_id"})
)
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Gallery name cannot be null")
    private String galleryName;
    private String description;

    @NotNull(message = "Gallery owner cannot be null")
    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)  // Creează relația cu Owner
    private Owner owner;


    @ManyToMany
    @JoinTable(
            name = "gallery_painting",
            joinColumns = @JoinColumn(name = "gallery_id"),
            inverseJoinColumns = @JoinColumn(name = "painting_id")
    )
    private List<Painting> paintings = new ArrayList<>();

    public Gallery() {}

    public Gallery(Owner owner, String galleryName, String description) {
        this.owner = owner;
        this.galleryName = galleryName;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Gallery{id=" + id + ", galleryName='" + galleryName + "'}";
    }

}
