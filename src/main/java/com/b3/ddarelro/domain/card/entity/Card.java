package com.b3.ddarelro.domain.card.entity;

import com.b3.ddarelro.domain.card.dto.request.CardModifyReq;
import com.b3.ddarelro.domain.comment.entity.Comment;
import com.b3.ddarelro.domain.common.BaseEntity;
import com.b3.ddarelro.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
