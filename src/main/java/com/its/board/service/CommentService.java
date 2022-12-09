package com.its.board.service;

import com.its.board.dto.CommentDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.entity.CommentEntity;
import com.its.board.repository.BoardRepository;
import com.its.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        BoardEntity entity = boardRepository.findById(commentDTO.getBoardId()).get(); // 조회할 게시판 번호
        CommentEntity commentEntity = CommentEntity.toSaveComment(entity, commentDTO);
//        commentDTO.setCommentWriter(commentDTO.getCommentWriter());
//        commentDTO.setCommentContents(commentDTO.getCommentContents());

       Long id = commentRepository.save(commentEntity).getId();

       return id;

    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        //select * from comment_table where board_id = ? db 쿼리가 필요함
        BoardEntity boardEntity = boardRepository.findById(boardId).get(); // 공통적으로 필요한 것

        //1 . comment_table에서 직접 해당 게시글의 댓글 목록을 가져오기
        List<CommentEntity> commentEntityList =
                commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        //2.  BoardEntity를 조회해서 댓글 목록 가져오기
//        List<CommentEntity> commentEntityList1 =
//                boardEntity.getCommentEntityList(); //2번 방법을 쓸때 @Transactional사용해야 한다

//        List<CommentEntity> commentEntityList = commentRepository.findAll();
        List<CommentDTO> commentList = new ArrayList<>();

        for (CommentEntity commentEntity : commentEntityList) {
            commentList.add(CommentDTO.coDTO(commentEntity));
        }
        return commentList;
    }


//    @Transactional
//    public CommentDTO findById(Long id){
//        Optional<CommentEntity> optionalCommentEntity = commentRepository.findById(id);
//
//        if (optionalCommentEntity.isPresent()){
//            return CommentDTO.coDTO(optionalCommentEntity.get());
//        }else {
//            return null;
//        }
//
//    }


}
