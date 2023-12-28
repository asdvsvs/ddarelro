package com.b3.ddarelro.domain.column.entity;

import com.b3.ddarelro.domain.board.entity.Board;
import com.b3.ddarelro.domain.common.BaseEntity;
import jakarta.persistence.Entity;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_column")
public class Column extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Long priority;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @Builder
    public Column(String title, Long priority, Boolean deleted, Board board) {
        this.title = title;
        this.priority = priority;
        this.deleted = deleted != null ? deleted : false;
        this.board = board;
    }

    public void update(String title) {
        this.title = title;
    }

    public void delete() {
        this.deleted = true;
    }

    public void restore() {
        this.deleted = false;
    }

    public void move(Long move) {
        this.priority = move;
    }
}
