package com.its.board.repository;

import com.its.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface BoardRepository extends JpaRepository<BoardEntity,Long> {

    // 조회수 증가쿼리 update board_table set board_hits =board_hits+1 where id=#{id}

    @Modifying // update. delete 쿼리로 사용할때 (entity 기준으로 쿼리로 사용)
    @Query(value = "update BoardEntity b set  b.boardHits=b.boardHits+1 where b.id = :id")
//    @Query(value = "update Board_table  set boardHits=boardHits+1 where id = :id",nativeQuery = true) // db쿼리를 적용할 때 뒤에 네이티브, 쿼리를 적용
    void updateHits(@Param("id") Long id);
    // select * from board_table where board_writer like '%q%'; order by id desc; 작성자검색
    List<BoardEntity> findByBoardWriterContainingOrderByIdDesc(String q); // findByBoardWriterContaining 작성자기준

    // select * from board_table where board_title like '%q%'; order by id desc; 작성자검색
    List<BoardEntity> findByBoardTitleContainingOrderByIdDesc(String q); // findByBoardWriterContaining 제목기준


    // select * from board_table where board_title like '%q%' or board_writer like '%q%'; order by id desc; 작성자,제목검색
    List<BoardEntity> findByBoardTitleContainingOrBoardWriterContainingOrderByIdDesc(String title,String writer); // findByBoardWriterContaining 작성자 , 제목
//    Optional<BoardEntity> findPass(String boardPassword);


//    @Modifying
//    @Query(update board_table set board_hits = board_hits + 1 where id=#{id})
//    int updateCount(Long id);
//    @Modifying
//    @Query("update board b set b.)
}
