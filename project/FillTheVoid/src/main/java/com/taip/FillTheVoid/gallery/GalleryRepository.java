package com.taip.FillTheVoid.gallery;

import com.taip.FillTheVoid.user.Owner.Owner;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GalleryRepository extends JpaRepository<Gallery, Integer> {

    @Query("SELECT g FROM Gallery g WHERE g.galleryName = :galleryName AND g.owner = :owner")
    Optional<Gallery> findByNameAndOwner(String galleryName, Owner owner);

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT g FROM Gallery g WHERE g.galleryName = :galleryName AND g.owner = :owner")
    Optional<Gallery> findByNameAndOwnerWithLock(String galleryName, Owner owner);

    @Query("SELECT g FROM Gallery g WHERE g.galleryName IN :galleryNames AND g.owner = :owner")
    List<Gallery> findByListNamesAndOwner(List<String> galleryNames, Owner owner);

    @Query("SELECT g.galleryName AS galleryName, g.description AS description FROM Gallery g WHERE g.owner = :owner")
    List<GalleryProjection> findAllByOwner(Owner owner);

    @Transactional
    @Modifying
    @Query("UPDATE Gallery g SET g.galleryName = :newName, g.description = :newDescription WHERE g = :gallery")
    int updateGalleryNameAndDescription(Gallery gallery, String newName, String newDescription);

    @Transactional
    @Modifying
    @Query("DELETE FROM Gallery g WHERE g = :gallery")
    int deleteGallery(Gallery gallery);
}
