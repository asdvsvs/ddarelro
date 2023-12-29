package com.b3.ddarelro.domain.userboard.repository;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.domain.userboard.entity.UserBoard;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {

    Optional<UserBoard> findByUserAndBoard(User user, Board board);

    Boolean existsByUserAndBoard(User user, Board board);

    List<UserBoard> findAllByUser(User foundUser);

    @Query("select count(ub) from UserBoard ub where ub.user = :user and ub.boardAuthority = 'ADMIN'")
    Long countAdminUserBoards(User user);
}
