package com.taip.FillTheVoid.badge;

import com.taip.FillTheVoid.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, String> {

    @Query("SELECT b FROM Badge b WHERE b.user.email = :email ORDER BY b.createdAt DESC")
    Optional<Badge> findLatestBadgeByUserEmail(@Param("email") String email);

}