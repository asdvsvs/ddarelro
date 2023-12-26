package com.b3.ddarelro.domain.board.entity;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    private ColorEnum color;

    @Builder
    private Board(String name, String description, ColorEnum color){
        this.name = name;
        this.description = description;
        this.deleted = false;
        this.color = color;
    }



}
