package com.example.travelagency.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {


    Optional<AppUser> findByEmail(String email);

    @Query("SELECT DISTINCT u FROM AppUser u JOIN u.trips t WHERE t.id IN :tripIds")
    List<AppUser> findByTripIds(@Param("tripIds") List<Long> tripIds);


    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = TRUE WHERE a.email = ?1")
    int enableAppUser(String email);
}
