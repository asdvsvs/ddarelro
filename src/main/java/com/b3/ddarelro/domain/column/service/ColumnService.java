package com.b3.ddarelro.domain.column.service;

import com.b3.ddarelro.domain.column.repository.ColumnRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColumnService {
    private final ColumnRepository columnRepository;

}
