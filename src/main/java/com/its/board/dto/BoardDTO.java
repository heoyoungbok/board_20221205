package com.its.board.dto;

import com.its.board.entity.BoardEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

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
      return boardDTO;
   }

}
