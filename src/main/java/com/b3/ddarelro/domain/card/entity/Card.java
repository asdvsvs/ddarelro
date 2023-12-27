package com.b3.ddarelro.domain.card.entity;

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
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList;

    @Builder
    public Card(String title, String content, String color, User user, List<Comment> commentList) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.user = user;
        this.commentList = commentList;
    }
}
