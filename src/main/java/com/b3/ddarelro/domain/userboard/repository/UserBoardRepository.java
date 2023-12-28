package com.b3.ddarelro.domain.userboard.repository;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.userboard.entity.UserBoard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {

    Optional<UserBoard> findByUserAndBoard(User user, Board board);

    Boolean existsByUserAndBoard(User user, Board board);

}
