package com.b3.ddarelro.domain.worker.entity;

import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.user.entity.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "tb_worker")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String workerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false)
    private Boolean isAssigned;

    @Builder
    public Worker(String workerName, User user, Card card, Boolean isAssigned) {
        this.workerName = workerName;
        this.user = user;
        this.card = card;
        this.isAssigned = isAssigned;
    }

//    public void setWorkerStatus() {
//        this.isAssigned = !isAssigned;
//    }
}
