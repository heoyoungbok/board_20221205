package com.its.board;

import com.its.board.dto.BoardDTO;
import com.its.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class BoardTest {
    @Autowired
    private BoardService boardService;


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
    public void boardSaveTest() {
        BoardDTO boardDTO = newBoard(1);

        Long savedId = boardService.save(boardDTO);
        BoardDTO savedBoard = boardService.findById(savedId);

        assertThat(boardDTO.getBoardWriter()).isEqualTo(savedBoard.getBoardWriter()); //라이터 나 타이틀 비교

    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("글작성 여러개")
    public void saveList(){
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



}
