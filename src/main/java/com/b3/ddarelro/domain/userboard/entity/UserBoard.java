package com.b3.ddarelro.domain.userboard.entity;


import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.common.BaseEntity;
import com.b3.ddarelro.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tb_userboard")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardAuthority boardAuthority;


    @Builder
    private UserBoard(User user, Board board, BoardAuthority authority) {
        this.user = user;
        this.board = board;
        this.boardAuthority = authority;
    }

    public void UpdateAuthority(BoardAuthority authority){
        this.boardAuthority = authority;
    }

    public void setBoard(Board board){
        this.board = board;
    }


}
