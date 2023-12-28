package com.b3.ddarelro.domain.user.repository;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);
}
