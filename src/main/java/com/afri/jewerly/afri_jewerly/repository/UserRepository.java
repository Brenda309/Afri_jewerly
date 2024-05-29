package com.afri.jewerly.afri_jewerly.repository;

import com.afri.jewerly.afri_jewerly.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Optional<Users> findByUsername(String username);

    @Query("SELECT u FROM Users u WHERE u.username = :usernameOrEmail OR u.email = :usernameOrEmail")
    Optional<Users> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
