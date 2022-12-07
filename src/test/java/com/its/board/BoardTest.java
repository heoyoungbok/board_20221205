package com.its.board;

import com.its.board.dto.BoardDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.repository.BoardRepository;
import com.its.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BoardTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;


//    @Test
//    @Transactional
//    @Rollback(value = true)
////    public void boardSaveTest() 글작성 객체 만들기
    private BoardDTO newBoard(int i) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setBoardWriter("testWriter" +i);
        boardDTO.setBoardPassword("testPassword"+i);
        boardDTO.setBoardTitle("testTitle"+i);
        boardDTO.setBoardContents("testContents"+i); // i = 일련 번호

        return boardDTO;
    }


    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("글작성 테스트")
    public void boardSaveTest() throws IOException {
        BoardDTO boardDTO = newBoard(1);

        Long savedId = boardService.save(boardDTO);
        BoardDTO savedBoard = boardService.findById(savedId);

        assertThat(boardDTO.getBoardWriter()).isEqualTo(savedBoard.getBoardWriter()); //라이터 나 타이틀 비교

    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("글작성 여러개")
    public void saveList() throws IOException {
        for (int i=1; i<20; i++){
            boardService.save(newBoard(i)); //샘플데이터로 테스트도 가능
        }

//        const temp =(id) =>{
//            console.log(id); 화살표 = 람다식
//        }
//

//        IntStream.rangeClosed(21,40).forEach(i->{  //람다식 rangeClosed = 21~40까지 숫자를 만든다 포잇츠로 21~40을 접근 게시글 , 댓글 , 회원 여러개가 필요할 때
//            boardService.save(newBoard(i));
//        });

    }

    @Test
    @Transactional
    @DisplayName("연관관계 조회")
    public void findTest(){
        // 파일이 첨부된 게시글 조회
        BoardEntity boardEntity = boardRepository.findById(20L).get();// 첨부 게시글 번호
        // 첨부파일의 originalFileName 조회
        System.out.println("boardEntity.getBoardFileEntityList()= " + boardEntity.getBoardFileEntityList().get(0).getOriginalFileName()); //부모테이블 자식테이블 조인해서 오리지날 파일 을 보여줌 (원래 db쿼리로는 두테이블 조인을 썻엇지만 jp를 쓰면 연관관계를 호출을 할 수 있음 . 그중에 오리지널 파일 네임만 쓰겟다 . )

        // native query
        //

    }

}
