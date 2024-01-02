//package com.b3.ddarelro.domain.worker.entity;
//
//import com.b3.ddarelro.domain.card.entity.*;
//import com.b3.ddarelro.domain.common.*;
//import com.b3.ddarelro.domain.user.entity.*;
//import jakarta.persistence.*;
//import java.time.*;
//import lombok.*;
//
//@Entity
//@Getter
//@Table(name = "tb_worker")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Worker extends BaseEntity {
//
//    @Id
//    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "card_id", nullable = false)
//    private Card card;
//
//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private Assignment assignment;
//    private LocalDateTime assignedAt;
//
//    @Builder
//    public Worker(User user, Card card, Assignment assignment) {
//        this.user = user;
//        this.card = card;
//        this.assignment = assignment;
//    }
//}
