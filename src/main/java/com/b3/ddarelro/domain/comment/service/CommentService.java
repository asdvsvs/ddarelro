package com.b3.ddarelro.domain.comment.service;

import com.b3.ddarelro.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

private final CommentRepository commentRepository;

}
