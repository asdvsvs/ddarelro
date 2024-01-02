package com.b3.ddarelro.domain.checklist.service;

import com.b3.ddarelro.domain.checklist.repository.CheckListRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CheckListDeleteRestoreService {

    private final CheckListRepository checkListRepository;

    public void deleteAllComment(List<Long> cardIds) {
        checkListRepository.SoftDelete(cardIds);
    }

    public void restoreAllComment(List<Long> cardIds) {
        checkListRepository.restoreAll(cardIds);
    }

}
