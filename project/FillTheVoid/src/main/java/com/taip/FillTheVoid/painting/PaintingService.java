package com.taip.FillTheVoid.painting;


import com.taip.FillTheVoid.gallery.Gallery;
import com.taip.FillTheVoid.gallery.GalleryProjection;
import com.taip.FillTheVoid.gallery.GalleryRepository;
import com.taip.FillTheVoid.gallery.GalleryService;
import com.taip.FillTheVoid.user.Owner.Owner;
import com.taip.FillTheVoid.user.User;
import com.taip.FillTheVoid.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaintingService {


    private final PaintingRepository paintingRepository;
    private final GalleryRepository galleryRepository;
    private final UserService userService;
    private final GalleryService galleryService;

    public String addPainting(String userEmail, String galleryName, String paintingName, String description, String author, String imageType, byte[] image) {


        User user = userService.getUserByEmail(userEmail);
        Owner owner = (Owner) user;

        LocalDateTime currentDateTime = LocalDateTime.now();

        Painting painting;

        if (Objects.equals(galleryName, "None")) {

            painting = new Painting(owner, null, paintingName, description, author, 0, currentDateTime, imageType, image, new ArrayList<>());
            paintingRepository.save(painting);
        }
        else {

            Gallery gallery = galleryService.getGalleryByNameAndOwner(galleryName, owner);

            painting = new Painting(owner, new ArrayList<>(), paintingName, description, author, 0, currentDateTime, imageType, image, new ArrayList<>());
            paintingRepository.save(painting);

            List <Painting> galleryPaintings = gallery.getPaintings();
            galleryPaintings.add(painting);
            gallery.setPaintings(galleryPaintings);

            galleryRepository.save(gallery);

            List<Gallery> paintingGalleries = painting.getGalleries();
            paintingGalleries.add(gallery);
            painting.setGalleries(paintingGalleries);

            paintingRepository.save(painting);



        }

        return "Painting with name " + paintingName + " was added";
    }

    public Painting getPainting(String paintingName, String userEmail) {

        User user = userService.getUserByEmail(userEmail);
        Owner owner = (Owner) user;

        Optional<Painting> painting =  paintingRepository.findByNameAndOwner(paintingName, owner);

        if (painting.isEmpty()) {
            throw new IllegalStateException("Pictura nu există cu acest nume");
        }

        return painting.get();
    }

    public Integer editPainting(String emailUser, String paintingName, String newPaintingName, String newDescription, String newAuthor) {

        User user = userService.getUserByEmail(emailUser);
        Owner owner = (Owner) user;

        Optional<Painting> alreadyPainting =  paintingRepository.findByNameAndOwner(newPaintingName, owner);

        if (alreadyPainting.isPresent() && !Objects.equals(paintingName, newPaintingName)) {
            throw new IllegalStateException("Pictura cu noul nume există deja");
        }

        Optional<Painting> painting =  paintingRepository.findByNameAndOwner(paintingName, owner);

        if (painting.isEmpty()) {
            throw new IllegalStateException("Pictura nu există cu acest nume");
        }

        return paintingRepository.updatePaintingNameAndDescriptionAndAuthor(painting.get(), newPaintingName, newDescription, newAuthor);


    }

    public Integer updatePaintingGallery(String emailUser, String paintingName, String galleryName) {
        // Fetch the user and ensure it's an Owner
        User user = userService.getUserByEmail(emailUser);
        Owner owner = (Owner) user;

        // Fetch the painting by name and owner
        Optional<Painting> paintingOptional = paintingRepository.findByNameAndOwner(paintingName, owner);
        if (paintingOptional.isEmpty()) {
            throw new IllegalStateException("Painting does not exist with this name.");
        }
        Painting painting = paintingOptional.get();

        // Fetch the new galleries by names and owner
        Gallery gallery = galleryService.getGalleryByNameAndOwner(galleryName, owner);

        if (gallery.getPaintings().contains(painting)) {
            return 0;
        }

        List <Painting> galleryPaintings = gallery.getPaintings();
        galleryPaintings.add(painting);
        gallery.setPaintings(galleryPaintings);

        galleryRepository.save(gallery);

        List<Gallery> paintingGalleries = painting.getGalleries();
        paintingGalleries.add(gallery);
        painting.setGalleries(paintingGalleries);

        paintingRepository.save(painting); // Save updated painting

        return 1; // Indicate success
    }

    public Integer removeFromGallery(String emailUser, String paintingName, String galleryName) {

        User user = userService.getUserByEmail(emailUser);
        Owner owner = (Owner) user;

        Optional<Painting> paintingOptional = paintingRepository.findByNameAndOwner(paintingName, owner);
        if (paintingOptional.isEmpty()) {
            throw new IllegalStateException("Painting does not exist with this name.");
        }
        Painting painting = paintingOptional.get();

        Gallery gallery = galleryService.getGalleryByNameAndOwner(galleryName, owner);

        if (!gallery.getPaintings().contains(painting)) {
            return 0;
        }

        List <Painting> galleryPaintings = gallery.getPaintings();
        galleryPaintings.remove(painting);
        gallery.setPaintings(galleryPaintings);

        galleryRepository.save(gallery);

        List<Gallery> paintingGalleries = painting.getGalleries();
        paintingGalleries.remove(gallery);
        painting.setGalleries(paintingGalleries);

        paintingRepository.save(painting); // Save updated painting

        return 1;
    }


    public Integer deletePainting(String emailUser, String paintingName) {

        User user = userService.getUserByEmail(emailUser);
        Owner owner = (Owner) user;

        Optional<Painting> painting =  paintingRepository.findByNameAndOwner(paintingName, owner);

        if (painting.isEmpty()) {
            throw new IllegalStateException("Pictura nu există cu acest nume");
        }

        return paintingRepository.deletePainting(painting.get());
    }

    public List<PaintingProjection> findAllByOwnerAndGallery(String emailUser, String galleryName) {

        User user = userService.getUserByEmail(emailUser);
        Owner owner = (Owner) user;

        Gallery gallery = galleryService.getGalleryByNameAndOwner(galleryName, owner);

        List<PaintingProjection> allPaintings = paintingRepository.findAllByOwnerAndGallery(owner, gallery);

        if (allPaintings == null) {
            throw new IllegalStateException("Picturile nu au fost gasite corespunzator");
        }

        return allPaintings;

    }

    public List<PaintingProjection> findAllByOwner(String emailUser) {

        User user = userService.getUserByEmail(emailUser);
        Owner owner = (Owner) user;

        List<PaintingProjection> allPaintings = paintingRepository.findAllByOwner(owner);

        if (allPaintings == null) {
            throw new IllegalStateException("Picturile nu au fost gasite corespunzator");
        }

        return allPaintings;
    }
}
