package com.taip.FillTheVoid.gallery;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GalleryController.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
public class GalleryControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GalleryService galleryService;


    @Test
    void testAddGalleryIntegration() throws Exception {
        // Test inputs
        String emailUser = "example@gmail.com";
        String galleryName = "Modern Art";
        String description = "A test gallery for modern art";

        // Mock the service response
        when(galleryService.addGallery(emailUser, galleryName, description))
                .thenReturn("Gallery created successfully");

        // Perform HTTP POST request using MockMvc
        mockMvc.perform(post("/api/v1/gallery/add")
                        .param("email-user", emailUser)
                        .param("gallery-name", galleryName)
                        .param("gallery-description", description))
                .andExpect(status().isOk()) // Assert HTTP status is 200 OK
                .andExpect(content().string("Gallery created successfully")); // Assert expected response content

        // Verify that the service was called with the correct parameters
        verify(galleryService).addGallery(emailUser, galleryName, description);
    }


    @Test
    void testFindAllByOwnerIntegration() throws Exception {
        // Test inputs
        String emailUser = "test@example.com";

        // Mock the service response
        when(galleryService.findAllByOwner(emailUser))
                .thenReturn(new ArrayList<>());

        // Perform HTTP POST request using MockMvc
        mockMvc.perform(get("/api/v1/gallery/findAllByOwner")
                        .param("email-user", emailUser))
                .andExpect(status().isOk()) // Assert HTTP status is 200 OK
                .andExpect(content().string("[]")); // Assert expected response content

        // Verify that the service was called with the correct parameters
        verify(galleryService).findAllByOwner(emailUser);
    }


    @Test
    void testEditGalleryIntegration() throws Exception {
        // Test inputs
        String emailUser = "example@gmail.com";
        String galleryName = "Art Gallery";
        String newGalleryName = "Updated Art Gallery";
        String newDescription = "New description for the gallery.";

        // Mock the service response
        when(galleryService.editGallery(emailUser, galleryName, newGalleryName, newDescription))
                .thenReturn(1);

        // Perform HTTP POST request using MockMvc
        mockMvc.perform(put("/api/v1/gallery/edit")
                        .param("email-user", emailUser)
                        .param("gallery-name", galleryName)
                        .param("new-gallery-name", newGalleryName)
                        .param("new-gallery-description", newDescription))
                .andExpect(status().isOk()) // Assert HTTP status is 200 OK
                .andExpect(content().string("1")); // Assert expected response content

        // Verify that the service was called with the correct parameters
        verify(galleryService).editGallery(emailUser, galleryName, newGalleryName, newDescription);
    }

    @Test
    void testDeleteGalleryIntegration() throws Exception {
        // Test inputs
        String emailUser = "test@example.com";
        String galleryName = "Modern Art";

        // Mock the service response
        when(galleryService.deleteGallery(emailUser, galleryName))
                .thenReturn(1);

        // Perform HTTP POST request using MockMvc
        mockMvc.perform(delete("/api/v1/gallery/delete")
                        .param("email-user", emailUser)
                        .param("gallery-name", galleryName))
                .andExpect(status().isOk()) // Assert HTTP status is 200 OK
                .andExpect(content().string("1")); // Assert expected response content

        // Verify that the service was called with the correct parameters
        verify(galleryService).deleteGallery(emailUser, galleryName);
    }

}
