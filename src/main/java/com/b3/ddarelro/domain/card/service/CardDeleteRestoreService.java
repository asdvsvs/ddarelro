package com.b3.ddarelro.domain.card.service;

import com.b3.ddarelro.domain.card.repository.*;
import java.util.*;
import lombok.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CardDeleteRestoreService {

    private final CardRepository cardRepository;


    public void deleteAllCard(List<Long> columnIds) {
        cardRepository.SoftDelete(columnIds);
    }

    public void restoreAllCard(List<Long> columnIds) {
        cardRepository.restoreAll(columnIds);
    }
}
