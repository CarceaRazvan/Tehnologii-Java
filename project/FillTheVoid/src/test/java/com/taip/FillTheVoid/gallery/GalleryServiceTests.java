package com.taip.FillTheVoid.gallery;

import com.taip.FillTheVoid.painting.Painting;
import com.taip.FillTheVoid.painting.PaintingRepository;
import com.taip.FillTheVoid.user.Owner.Owner;
import com.taip.FillTheVoid.user.Owner.OwnerRepository;
import com.taip.FillTheVoid.user.Role;
import com.taip.FillTheVoid.user.UserService;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ExtendWith(SpringExtension.class)
@Import({UserService.class, GalleryService.class})

// Ensure GalleryService is loaded for testing
public class GalleryServiceTests {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private GalleryRepository galleryRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PaintingRepository paintingRepository;

    @Autowired
    private UserService userService;  // Add UserService
    @Autowired
    private GalleryService galleryService; // Inject the real GalleryService

    private Owner owner;
    private Owner owner2;
    private Gallery gallery;
    private List<Painting> paintings;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        owner = new Owner();
        owner.setFirstName("John");
        owner.setLastName("Doe");
        owner.setEmail("example@gmail.com");
        owner.setPassword("1234");
        owner.setRole(Role.USER);
        owner.setPaintings(new ArrayList<>());
        owner.setGalleries(new ArrayList<>());

        Owner owner2 = new Owner();
        owner2.setFirstName("John");
        owner2.setLastName("Doe");
        owner2.setEmail("example2@gmail.com");
        owner2.setPassword("1234");
        owner2.setRole(Role.USER);
        owner2.setPaintings(new ArrayList<>());
        owner2.setGalleries(new ArrayList<>());


        ownerRepository.save(owner2);

        // Save owner to repository
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

        gallery = new Gallery();
        gallery.setGalleryName("Art Gallery");
        gallery.setOwner(owner);
        gallery.setDescription("Gallery description");
        gallery.setPaintings(paintings);

        paintingRepository.save(painting);
        galleryRepository.save(gallery);
        paintings.add(painting);
    }

    @Test
    public void testAddGallery() {
        // Arrange
        String userEmail = "example@gmail.com";
        String galleryName = "Modern Art";
        String description = "A gallery for modern art pieces";

        // Act
        String result = galleryService.addGallery(userEmail, galleryName, description);

        // Assert
        Assertions.assertThat(result).isEqualTo("Gallery with name Modern Art was added");

        Optional<Gallery> savedGalleryOpt = galleryRepository.findAll().stream()
                .filter(g -> g.getGalleryName().equals(galleryName))
                .findFirst();

        Assertions.assertThat(savedGalleryOpt).isPresent();
        Gallery savedGallery = savedGalleryOpt.get();

        Assertions.assertThat(savedGallery.getGalleryName()).isEqualTo(galleryName);
        Assertions.assertThat(savedGallery.getDescription()).isEqualTo(description);
        Assertions.assertThat(savedGallery.getOwner().getEmail()).isEqualTo(userEmail);
    }

    @Test
    public void testAddGalleryInvalidUser() {
        // Arrange
        String invalidUserEmail = "invalid@example.com";
        String galleryName = "Invalid Gallery";
        String description = "Should not be added";

        // Act & Assert
        Assertions.assertThatThrownBy(() -> galleryService.addGallery(invalidUserEmail, galleryName, description))
                .isInstanceOf(IllegalStateException.class)  // Change the expected exception to IllegalStateException
                .hasMessageContaining("Utilizatorul nu există cu acest email");
    }

    @Test
    public void testGetGalleryByNameAndOwner_Success() {
        // Arrange
        String galleryName = "Art Gallery";

        // Mock the repository to return the gallery
        Gallery expectedGallery = galleryRepository.findByNameAndOwner(galleryName, owner).get();

        // Act
        Gallery savedGallery = galleryService.getGalleryByNameAndOwner(galleryName, owner);

        // Assert
        Assertions.assertThat(savedGallery).isNotNull();
        Assertions.assertThat(savedGallery).isEqualTo(expectedGallery);
    }

    @Test
    public void testGetGalleryByNameAndOwner_GalleryNotFound() {
        // Arrange
        String galleryName = "Nonexistent Gallery";

        // Act & Assert
        Assertions.assertThatThrownBy(() -> galleryService.getGalleryByNameAndOwner(galleryName, owner))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Galeria nu există cu acest nume");
    }

    @Test
    public void testGetGalleriesByNamesAndOwner_PartialMatch() {

        List<String> galleryNames = List.of("Art Gallery", "Nonexistent Gallery");

        List<Gallery> galleries = galleryService.getGalleriesByNamesAndOwner(galleryNames, owner);

        Assertions.assertThat(galleries).isNotNull();
        Assertions.assertThat(galleries).hasSize(1);
        Assertions.assertThat(galleries).extracting(Gallery::getGalleryName)
                .containsExactly("Art Gallery");

    }

    @Test
    public void testFindAllByOwner_Success() {

        List<GalleryProjection> galleryProjections = galleryService.findAllByOwner("example@gmail.com");


        // Assert
        Assertions.assertThat(galleryProjections).isNotNull();
        Assertions.assertThat(galleryProjections).hasSize(1);
    }

    @Test
    public void testEditGallery_Success() {

        String newGalleryName = "Updated Art Gallery";
        String newDescription = "New description for the gallery.";


        int result = galleryService.editGallery(owner.getEmail(), gallery.getGalleryName(), newGalleryName, newDescription);

        Assertions.assertThat(result).isEqualTo(1);

        entityManager.clear();

        Gallery updatedGallery = galleryRepository.findById(gallery.getId()).orElseThrow();

        Assertions.assertThat(updatedGallery.getGalleryName()).isEqualTo(newGalleryName);
        Assertions.assertThat(updatedGallery.getDescription()).isEqualTo(newDescription);
    }

    @Test
    public void testDeleteGallery_Success() {
        // Act: Call the service to delete the gallery
        int result = galleryService.deleteGallery(owner.getEmail(), gallery.getGalleryName());

        // Assert: Verify that the result indicates success (1 or rows affected)
        Assertions.assertThat(result).isEqualTo(1);

        entityManager.clear();

        // Verify that the gallery is deleted from the repository
        Gallery deletedGallery = galleryRepository.findById(gallery.getId()).orElse(null);
        Assertions.assertThat(deletedGallery).isNull();
    }

    @Test
    public void testDeleteGallery_GalleryNotFound() {
        // Act & Assert: Try to delete a non-existent gallery
        String nonExistentGalleryName = "NonExistent Gallery";

        // Assert: The result should be 0, indicating no rows were deleted
        Assertions.assertThatThrownBy(() -> galleryService.deleteGallery(owner.getEmail(), nonExistentGalleryName))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Galeria nu există cu acest nume");

    }


}
