package com.taip.FillTheVoid.gallery;

import com.taip.FillTheVoid.painting.Painting;
import com.taip.FillTheVoid.painting.PaintingRepository;
import com.taip.FillTheVoid.user.Owner.Owner;
import com.taip.FillTheVoid.user.Owner.OwnerRepository;
import com.taip.FillTheVoid.user.Role;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(SpringExtension.class)
public class GalleryRepositoryTests {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    private Owner owner;
    private List<Painting> paintings;
    @Autowired
    private PaintingRepository paintingRepository;

    @BeforeEach
    public void setUp() {
        owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setEmail("example@gmail.com");
        owner.setPassword("1234");
        owner.setRole(Role.USER);
        owner.setPaintings(new ArrayList<>());
        owner.setGalleries(new ArrayList<>());

        ownerRepository.save(owner);

        paintings = new ArrayList<>();
        Painting painting = new Painting();
        painting.setPaintingName("Mona Lisa");
        painting.setDescription("1500s image");
        painting.setAuthor("Leonardo da Vinci");
        painting.setNoVisualizations(0);
        painting.setCreatedAt(LocalDateTime.now());
        painting.setImage(null);
        painting.setComments(null);
        painting.setGalleries(null);
        painting.setOwner(owner);

        paintingRepository.save(painting);
        paintings.add(painting);

    }

    @Test
    public void testSaveValidGallery() {
        // Arrange
        Gallery gallery = Gallery.builder()
                .galleryName("Classic paintings")
                .description("Old paintings from 1800s")
                .owner(owner)
                .paintings(paintings)
                .build();

        Gallery gallery2 = new Gallery(owner, "Classic paintings 2", "Old paintings from 1600s");

        // Act
        Gallery savedGallery = galleryRepository.save(gallery);
        Gallery savedGallery2 = galleryRepository.save(gallery2);


        // Assert
        Assertions.assertThat(savedGallery).isNotNull();
        Assertions.assertThat(savedGallery.getGalleryName()).isEqualTo("Classic paintings");
        Assertions.assertThat(savedGallery.getDescription()).isEqualTo("Old paintings from 1800s");
        Assertions.assertThat(savedGallery.getOwner()).isEqualTo(owner);
        Assertions.assertThat(new ArrayList<>(savedGallery.getPaintings())).isEqualTo(new ArrayList<>(paintings));
        Assertions.assertThat(savedGallery.getId()).isGreaterThan(0);

        Assertions.assertThat(savedGallery2).isNotNull();
        Assertions.assertThat(savedGallery2.getGalleryName()).isEqualTo("Classic paintings 2");
        Assertions.assertThat(savedGallery2.getDescription()).isEqualTo("Old paintings from 1600s");
        Assertions.assertThat(savedGallery2.getOwner()).isEqualTo(owner);
        Assertions.assertThat(new ArrayList<>(savedGallery2.getPaintings())).isEqualTo(new ArrayList<>());
        Assertions.assertThat(savedGallery2.getId()).isGreaterThan(0);
    }

    @Test
    public void testSaveGalleryWithNullOwner() {
        // Arrange
        Gallery gallery = Gallery.builder()
                .galleryName("Classic paintings")
                .description("Old paintings from 1800s")
                .owner(null)
                .paintings(new ArrayList<>())
                .build();

        // Act & Assert
        Assertions.assertThatThrownBy(() -> galleryRepository.save(gallery))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Gallery owner cannot be null");
    }

    @Test
    public void testFindGalleryByNameAndOwner() {
        // Arrange
        Gallery gallery = Gallery.builder()
                .galleryName("Classic paintings")
                .description("Old paintings from 1800s")
                .owner(owner)
                .paintings(paintings)
                .build();

        galleryRepository.save(gallery);

        // Act
        Gallery foundGallery = galleryRepository.findByNameAndOwner("Classic paintings", owner).orElse(null);

        // Assert
        Assertions.assertThat(foundGallery).isNotNull();
        Assertions.assertThat(foundGallery.getGalleryName()).isEqualTo("Classic paintings");
        Assertions.assertThat(foundGallery.getDescription()).isEqualTo("Old paintings from 1800s");
        Assertions.assertThat(foundGallery.getOwner()).isEqualTo(owner);
        Assertions.assertThat(new ArrayList<>(foundGallery.getPaintings())).isEqualTo(new ArrayList<>(paintings));
        Assertions.assertThat(foundGallery.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindAllByOwner() {

        // Arrange
        Gallery gallery1 = Gallery.builder()
                .galleryName("Classic paintings")
                .description("Old paintings from the 1800s")
                .owner(owner)
                .paintings(paintings)
                .build();

        Gallery gallery2 = Gallery.builder()
                .galleryName("Modern art")
                .description("Art from the 21st century")
                .owner(owner)
                .paintings(new ArrayList<>())
                .build();

        galleryRepository.save(gallery1);
        galleryRepository.save(gallery2);

        // Act
        List<GalleryProjection> foundGalleries = galleryRepository.findAllByOwner(owner);

        // Assert
        Assertions.assertThat(foundGalleries).hasSize(2);
        Assertions.assertThat(foundGalleries.get(0).getGalleryName()).isEqualTo("Classic paintings");
        Assertions.assertThat(foundGalleries.get(0).getDescription()).isEqualTo("Old paintings from the 1800s");
        Assertions.assertThat(foundGalleries.get(1).getGalleryName()).isEqualTo("Modern art");
        Assertions.assertThat(foundGalleries.get(1).getDescription()).isEqualTo("Art from the 21st century");
    }

    @Test
    public void testUpdateGalleryNameAndDescription() {
        // Arrange
        Gallery gallery = Gallery.builder()
                .galleryName("Classic paintings")
                .description("Old paintings from 1800s")
                .owner(owner)
                .paintings(paintings)
                .build();

        Gallery savedGallery = galleryRepository.save(gallery);

        // Act
        int updatedRows = galleryRepository.updateGalleryNameAndDescription(savedGallery, "Updated Gallery Name", "Updated Description");

        // Assert
        Assertions.assertThat(updatedRows).isEqualTo(1);

        entityManager.clear();

        Gallery updatedGallery = galleryRepository.findById(savedGallery.getId()).orElse(null);

        Assertions.assertThat(updatedGallery).isNotNull();
        Assertions.assertThat(updatedGallery.getGalleryName()).isEqualTo("Updated Gallery Name");
        Assertions.assertThat(updatedGallery.getDescription()).isEqualTo("Updated Description");
    }

    @Test
    public void testDeleteGallery() {
        // Arrange
        Gallery gallery = Gallery.builder()
                .galleryName("Classic paintings")
                .description("Old paintings from 1800s")
                .owner(owner)
                .paintings(paintings)
                .build();

        Gallery savedGallery = galleryRepository.save(gallery);

        // Act
        int deletedRows = galleryRepository.deleteGallery(savedGallery);

        // Assert
        Assertions.assertThat(deletedRows).isEqualTo(1);

        entityManager.clear();

        Gallery deletedGallery = galleryRepository.findById(savedGallery.getId()).orElse(null);
        Assertions.assertThat(deletedGallery).isNull();
    }

    @Test
    void testToString() {
        // Arrange
        Gallery gallery = new Gallery();
        gallery.setId(1); // Assuming there's a setter for 'id'
        gallery.setGalleryName("Art of the World"); // Assuming there's a setter for 'galleryName'

        // Act
        String result = gallery.toString();

        // Assert
        String expected = "Gallery{id=1, galleryName='Art of the World'}";
        Assertions.assertThat(result).isEqualTo(expected);
    }

}
