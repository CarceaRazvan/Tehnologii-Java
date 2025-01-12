package com.taip.FillTheVoid.painting;

import org.springframework.core.io.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/v1/painting")
@RequiredArgsConstructor
public class PaintingController {

    private final PaintingService paintingService;

    @RequestMapping(
            path = "/add",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addPainting(
            @RequestParam("email-user") String emailUser,
            @RequestParam("gallery-name") String galleryName,
            @RequestParam("painting-name") String paintingName,
            @RequestParam(value = "painting-description", required = false) String description,
            @RequestParam(value = "author", required = false) String author,
            @RequestPart("image") MultipartFile image)  throws Exception{

        System.out.println("+"+emailUser+"+");


        return ResponseEntity.ok(paintingService.addPainting(emailUser, galleryName, paintingName, description, author, image.getContentType(), image.getBytes()));
    }

    @GetMapping("/get/{fileName}")
    public ResponseEntity<Resource> getPainting(@RequestParam("email-user") String emailUser, @PathVariable String fileName) {

            Painting painting = paintingService.getPainting(fileName, emailUser);
            var image = new ByteArrayResource(painting.getImage());

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, painting.getImageType())
                .cacheControl(CacheControl.noStore().cachePrivate().mustRevalidate())
                .body(image);
    }

    @GetMapping("/findAllByOwnerAndGallery")
    public ResponseEntity<List<PaintingProjection>> getAllPaintingsFromGallery(
            @RequestParam("email-user") String emailUser,
            @RequestParam("gallery-name") String galleryName) {

        return ResponseEntity.ok(paintingService.findAllByOwnerAndGallery(emailUser, galleryName));

    }

    @GetMapping("/findAllByOwner")
    public ResponseEntity<List<PaintingProjection>> getAllPaintings(
            @RequestParam("email-user") String emailUser) {

        return ResponseEntity.ok(paintingService.findAllByOwner(emailUser));
    }

    @PutMapping("/edit")
    public ResponseEntity<Integer> editPainting(
            @RequestParam("email-user") String emailUser,
            @RequestParam("painting-name") String paintingName,
            @RequestParam("new-painting-name") String newPaintingName,
            @RequestParam("new-painting-description") String newDescription,
            @RequestParam("new-author") String newAuthor) {

        System.out.println("+"+paintingName+"+");

        return ResponseEntity.ok(paintingService.editPainting(emailUser, paintingName, newPaintingName, newDescription, newAuthor));

    }

    @PutMapping("/editGallery")
    public ResponseEntity<Integer> editPaintingGallery(
            @RequestParam("email-user") String emailUser,
            @RequestParam("painting-name") String paintingName,
            @RequestParam("gallery-name") String galleryName) {

        return ResponseEntity.ok(paintingService.updatePaintingGallery(emailUser, paintingName, galleryName));
    }

    @PutMapping("/removeFromGallery")
    public ResponseEntity<Integer> removeFromGallery(
            @RequestParam("email-user") String emailUser,
            @RequestParam("painting-name") String paintingName,
            @RequestParam("gallery-name") String galleryName) {

        return ResponseEntity.ok(paintingService.removeFromGallery(emailUser, paintingName, galleryName));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Integer> deletePainting(

            @RequestParam("email-user") String emailUser,
            @RequestParam("painting-name") String paintingName) {

        return ResponseEntity.ok(paintingService.deletePainting(emailUser, paintingName));
    }

}
