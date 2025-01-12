package com.taip.FillTheVoid.gallery;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gallery")
@RequiredArgsConstructor
public class GalleryController {


    private final GalleryService galleryService;

    @PostMapping("/add")
    public ResponseEntity<String> addGallery(
            @RequestParam("email-user") String emailUser,
            @RequestParam("gallery-name") String galleryName,
            @RequestParam(value = "gallery-description", required = false) String description) {

        return ResponseEntity.ok(galleryService.addGallery(emailUser, galleryName, description));
    }

    @GetMapping("/findAllByOwner")
    public ResponseEntity<List<GalleryProjection>> findGalleries(
            @RequestParam("email-user") String emailUser) {

        return ResponseEntity.ok(galleryService.findAllByOwner(emailUser));
    }

    @PutMapping("/edit")
    public ResponseEntity<Integer> editGallery(

            @RequestParam("email-user") String emailUser,
            @RequestParam("gallery-name") String galleryName,
            @RequestParam("new-gallery-name") String newGalleryName,
            @RequestParam("new-gallery-description") String newDescription) {

        return ResponseEntity.ok(galleryService.editGallery(emailUser, galleryName, newGalleryName, newDescription));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deleteGallery(

            @RequestParam("email-user") String emailUser,
            @RequestParam("gallery-name") String galleryName) {

        return ResponseEntity.ok(galleryService.deleteGallery(emailUser, galleryName));
    }


}
