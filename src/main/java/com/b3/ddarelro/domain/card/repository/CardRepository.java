package com.b3.ddarelro.domain.card.repository;

import com.b3.ddarelro.domain.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card,Long> {

}
