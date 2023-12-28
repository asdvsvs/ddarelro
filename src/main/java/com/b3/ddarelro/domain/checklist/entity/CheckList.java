package com.b3.ddarelro.domain.checklist.entity;

import com.b3.ddarelro.domain.card.entity.Card;
import com.b3.ddarelro.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_check_list")
public class CheckList extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Boolean completed;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    @Builder
    public CheckList(String content, Card card) {
        this.content = content;
        this.card = card;
        this.completed = false;
        this.deleted = false;
    }

    public void completeToggle() {
        this.completed = !completed;
    }
}