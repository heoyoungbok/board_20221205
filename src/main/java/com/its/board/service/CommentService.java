package com.its.board.service;

import com.its.board.dto.CommentDTO;
import com.its.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    public Long save(CommentDTO commentDTO) {
      commentRepository.save(commentDTO);
    }

    public List<CommentDTO> findAll(Long id) {
    }
}
