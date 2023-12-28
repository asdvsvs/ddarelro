package com.b3.ddarelro.domain.card.entity;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.comment.entity.*;
import com.b3.ddarelro.domain.common.*;
import com.b3.ddarelro.domain.user.entity.*;
import jakarta.persistence.*;
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
    private Date dueDate;
    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @Builder
    public Card(String name, String description, String color, Boolean deleted, User user,
        List<Comment> commentList) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.deleted = deleted;
        this.user = user;
        this.commentList = commentList;
    }

    public void modifyCard(CardModifyReq reqDto) {
        this.name = reqDto.name();
        this.description = reqDto.description();
        this.color = reqDto.color();
    }

    public void deleteCard() {
        this.deleted = !this.deleted;
    }

    public Date setDueDate() {
    }
}
