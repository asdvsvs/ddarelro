package com.b3.ddarelro.domain.card.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {
 private final CardRepository cardRepository;
}
