package com.its.board.service;

import com.its.board.dto.BoardDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.its.board.entity.BoardEntity.toSaveEntity;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public Long save(BoardDTO boardDTO) {
        BoardEntity boardEntity = toSaveEntity(boardDTO);
//        Long saveId = boardRepository.save(BoardEntity.toSaveEntity(boardDTO)).getId();
//        return saveId;
        return boardRepository.save(boardEntity).getId();
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntityList){
            boardDTOList.add(BoardDTO.toDTO(boardEntity));
        }
        return boardDTOList;
    }

    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);


        if (optionalBoardEntity.isPresent()){
            return BoardDTO.toDTO(optionalBoardEntity.get());

        }else{
            return null;
        }
    }

    @Transactional               // 커스텀 쿼리를 사용 할 때 사용 , 참조관계에서 사용
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
//            Optional<BoardEntity> countHits = boardRepository.findById(id);

//        b   boardRepository.updateHits(countHits);

    }

//    public BoardDTO findPass(String boardPassword){
//        Optional<BoardEntity> optionalBoardEntity =boardRepository.findPass(boardPassword);
//       if (optionalBoardEntity.isPresent()){
//           return BoardDTO.toDTO(optionalBoardEntity.get());
//       }else {
//           return null;
//       }
//    }

    public void update(BoardDTO boardDTO) {
        BoardEntity updateEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(updateEntity);
//        boardRepository.save(updateEntity);
    }


    public BoardDTO findByPass(String boardPassword) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findPass(boardPassword);
        if (optionalBoardEntity.isPresent()){
            return BoardDTO.toDTO(optionalBoardEntity.get());
        }else {
            return null;
        }
    }


//   public void updateCount(Long id ,BoardDTO boardDTO) {
//      Board board = boardRepository.findById(id)
//   }
}
