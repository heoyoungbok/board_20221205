package com.its.board.repository;

import com.its.board.dto.BoardDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.service.BoardService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
//    @Modifying
//    @Query(update board_table set board_hits = board_hits + 1 where id=#{id})
//    int updateCount(Long id);
    @Modifying
    @Query("update board b set b.boardHits = b.boardHits +1 where b.id = #{id}")
}
