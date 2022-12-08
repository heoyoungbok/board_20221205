package com.its.board.service;

import com.its.board.dto.CommentDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.entity.CommentEntity;
import com.its.board.repository.BoardRepository;
import com.its.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        String commentWriter = commentDTO.getCommentWriter();
        String commentContents = commentDTO.getCommentContents();
        BoardEntity entity = boardRepository.findById(commentDTO.getId()).get();
        CommentEntity commentEntity = CommentEntity.toSaveComment(entity, commentWriter, commentContents);
        return commentRepository.save(commentEntity).getId();

    }

    @Transactional
    public List<CommentDTO> findAll() {
        List<CommentEntity> commentEntityList = commentRepository.findAll();
        List<CommentDTO> commentList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            CommentDTO commentDTO = CommentDTO.coDTO(commentEntity);
            commentList.add(commentDTO);
        }
        return commentList;
    }


    @Transactional
    public CommentDTO findById(Long id){
        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);

        if (optionalCommentEntity.isPresent()){
            return CommentDTO.coDTO(optionalCommentEntity.get());
        }else {
            return null;
        }

    }


}
