package com.b3.ddarelro.domain.board.repository;

import com.b3.ddarelro.domain.board.entity.Board;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Optional<Board> findByName(String name);

    List<Board> findAllByDeletedFalse(Sort sort);

}
