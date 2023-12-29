package com.b3.ddarelro.domain.user.repository;

import com.b3.ddarelro.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u "
        + "WHERE u.email = :email AND u.deleted = false")
    Optional<User> findByEmailAndNotDeleted(String email);

}
