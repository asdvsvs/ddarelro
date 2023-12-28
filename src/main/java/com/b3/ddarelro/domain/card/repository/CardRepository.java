package com.b3.ddarelro.domain.card.repository;

import com.b3.ddarelro.domain.card.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface CardRepository extends JpaRepository<Card, Long> {

    List<Card> findAllByOrderByCreatedAtDesc();
}
