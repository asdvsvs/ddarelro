package com.b3.ddarelro.domain.card.service;

import com.b3.ddarelro.domain.card.dto.request.*;
import com.b3.ddarelro.domain.card.dto.response.*;
import com.b3.ddarelro.domain.card.entity.*;
import com.b3.ddarelro.domain.card.repository.*;
import com.b3.ddarelro.domain.column.service.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final ColumnService columnService;

    public CardCreateResDto createCard(CardCreateReqDto reqDto) {
//        columnService.findColumn(reqDto.getColumnId()).orElseThrow(() -> new GlobalException());
        Card newCard = Card.builder()
            .title(reqDto.getTitle())
            //.user(user)
            .content(reqDto.getContent())
            .color(reqDto.getColor())
            .build();
        cardRepository.save(newCard);
        return CardCreateResDto.formingWith(newCard);
    }
}
