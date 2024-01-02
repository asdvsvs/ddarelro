package com.b3.ddarelro.domain.card.entity;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.common.*;
import com.b3.ddarelro.domain.worker.entity.*;
import jakarta.persistence.*;
import java.time.*;
import java.util.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_card")
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "card_id", nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String color;
    private LocalDate dueDate;
    private Boolean deleted;
    private Long priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "column_id")
    private com.b3.ddarelro.domain.column.entity.Column column;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Worker> worker;

    @Builder
    public Card(String name, String description, String color, Boolean deleted, Long priority,
        com.b3.ddarelro.domain.column.entity.Column column) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.deleted = deleted != null ? deleted : false;
        this.priority = priority;
        this.column = column;
    }

    public void modifyCard(CardModifyReq reqDto) {
        this.name = reqDto.name();
        this.description = reqDto.description();
        this.color = reqDto.color();
    }

    public void deleteRestoreCard() {
        this.deleted = !this.deleted;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public void moveSpot(Long spot) {
        this.priority = spot;
    }

    public void changeColumn(com.b3.ddarelro.domain.column.entity.Column column) {
        this.column = column;
    }
}
