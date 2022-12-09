package com.its.board.repository;

import com.its.board.entity.BoardEntity;
import com.its.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {

        //select * from comment_table board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity); //엔티티 값이 넘어와야 ib값을 넣어줌 최근 댓글을 쓴 순으로 정렬
}
