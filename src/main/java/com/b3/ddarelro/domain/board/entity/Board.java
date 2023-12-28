package com.b3.ddarelro.domain.board.entity;

import com.b3.ddarelro.domain.common.BaseEntity;
import com.b3.ddarelro.domain.userboard.entity.UserBoard;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_board")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Boolean deleted;

    @Enumerated(EnumType.STRING)
    private Color color;

    @OneToMany(mappedBy = "board")
    private List<UserBoard> userBoardList = new ArrayList<>();

    @Builder
    private Board(String name, String description, Color color){
        this.name = name;
        this.description = description;
        this.deleted = false;
        this.color = color;
    }

    public void addUserBoard(UserBoard userBoard) {
        this.userBoardList.add(userBoard);
        userBoard.setBoard(this);
    }

    public void updateBoardState(Boolean bool){
        this.deleted = bool;
    }

}
