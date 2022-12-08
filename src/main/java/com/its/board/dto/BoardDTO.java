package com.its.board.dto;

import com.its.board.entity.BoardEntity;
import com.its.board.entity.BoardFileEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardDTO {
   private Long id;
   private String boardWriter;
   private String boardPassword;
   private String boardTitle;
   private String boardContents;

   private LocalDateTime boardCreatedTime;
   private LocalDateTime boardUpCreatedTime;

   private int boardHits;

//   private MultipartFile boardFile;   // 단수 단일
   private List<MultipartFile> boardFile;   //리스트형태로 하면 복수
   private int fileAttached;
   private List<String> originalFileName; // 리스트 타입으로 변경
   private List<String> storedFileName;



   public static BoardDTO toDTO(BoardEntity boardEntity){
      BoardDTO boardDTO = new BoardDTO();
      boardDTO.setId(boardEntity.getId());
      boardDTO.setBoardWriter(boardEntity.getBoardWriter());
      boardDTO.setBoardPassword(boardEntity.getBoardPassword());
      boardDTO.setBoardTitle(boardEntity.getBoardTitle());
      boardDTO.setBoardContents(boardEntity.getBoardContents());
      boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
      boardDTO.setBoardUpCreatedTime(boardEntity.getUpdatedTime());
      boardDTO.setBoardHits(boardEntity.getBoardHits());



      // 파일 관련된 내용추가
     if (boardEntity.getFileAttached() ==1){
        //첨부파일 있음
        boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
        List<String> originalFileNameList = new ArrayList<>();
        List<String> storedFileNameList = new ArrayList<>();
         //첨부파일 이름가져옴
        for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()){ // 포잇츠 문법
           //BoardDTO의 originalFileName이 List이기 때문에 add()를 이용하여
           // boardFileEntity에 있는 originalFileName을 옮겨 담음 .
          originalFileNameList.add(boardFileEntity.getOriginalFileName());
          storedFileNameList.add(boardFileEntity.getStoredFileName());
        }
        //보드 엔티티에 접근하여 자식테이블 보드파일엔티티 리스트를 사용하게 만듬
        // 트렌젝션 , (findAll , findById ) => eager (부모객체를 조회할때 필요하지 않든 자식테이터 같이 가져옴), lazy (필요할 때 호출)
//        boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
//        boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
        boardDTO.setOriginalFileName(originalFileNameList);
        boardDTO.setStoredFileName(storedFileNameList);

     }else {
        //첨부파일 없음
        boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0

     }


      return boardDTO;
   }



}
