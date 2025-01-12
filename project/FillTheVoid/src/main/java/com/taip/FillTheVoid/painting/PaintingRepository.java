package com.taip.FillTheVoid.painting;

import com.taip.FillTheVoid.gallery.Gallery;
import com.taip.FillTheVoid.user.Owner.Owner;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PaintingRepository extends JpaRepository<Painting, Integer> {

    @Query("SELECT p FROM Painting p WHERE p.paintingName = :paintingName AND p.owner = :owner")
    Optional<Painting> findByNameAndOwner(String paintingName, Owner owner);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Painting p WHERE p.paintingName = :paintingName AND p.owner = :owner")
    Optional<Painting> findByNameAndOwnerWithLock(String paintingName, Owner owner);

//    @Modifying
//    @Transactional
//    @Query("INSERT INTO Gallery_Painting (gallery_id, painting_id) VALUES (:galleryId, :paintingId)")
//    void addPaintingToGallery(@Param("galleryId") Integer galleryId, @Param("paintingId") Integer paintingId);

    @Query("SELECT p.paintingName AS paintingName, p.description AS description, p.author AS author, p.imageType AS imageType, p.image AS image " +
            "FROM Painting p JOIN p.galleries g WHERE p.owner = :owner AND g = :gallery")
    List<PaintingProjection> findAllByOwnerAndGallery(Owner owner, Gallery gallery);

    @Query("SELECT p.paintingName AS paintingName, p.description AS description, p.author AS author, p.imageType AS imageType, p.image AS image FROM Painting p WHERE p.owner = :owner")
    List<PaintingProjection> findAllByOwner(Owner owner);

    @Transactional
    @Modifying
    @Query("UPDATE Painting p SET p.paintingName = :newName, p.description = :newDescription, p.author = :newAuthor WHERE p = :painting")
    int updatePaintingNameAndDescriptionAndAuthor(Painting painting, String newName, String newDescription, String newAuthor);

    @Transactional
    @Modifying
    @Query("DELETE FROM Painting p WHERE p = :painting")
    int deletePainting(Painting painting);


}
