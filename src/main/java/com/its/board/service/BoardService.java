package com.its.board.service;

import com.its.board.dto.BoardDTO;
import com.its.board.entity.BoardEntity;
import com.its.board.entity.BoardFileEntity;
import com.its.board.repository.BoardFileRepository;
import com.its.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public Long save(BoardDTO boardDTO) throws IOException {
//        if (boardDTO.getBoardFile().isEmpty()){ 단수 일때
            if (boardDTO.getBoardFile() == null || boardDTO.getBoardFile().size() == 0 ){
                System.out.println("파일 없음");
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            return boardRepository.save(boardEntity).getId();
        }else {
                System.out.println("파일 있음");
                // 게시글 정보를 먼저 저장하고 해당 게시글의 entity를 가져옴
                BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDTO);
                Long savedId =boardRepository.save(boardEntity).getId();
                BoardEntity entity = boardRepository.findById(savedId).get();
                // 파일이 담긴 list를 반복문으로 접근하여 하나씩 이름 가져오고, 저장용 이름 만들고
                // 로컬 경로에 저장하고 board_file_table에 저장
                for (MultipartFile boardFile: boardDTO.getBoardFile()){


//                  MultipartFile boardFile = boardDTO.getBoardFile(); 단수일때는 필요하였지만 반복문이 있어서 필요가 없음
                    String originalFileName = boardFile.getOriginalFilename();
                    String storedFileName = System.currentTimeMillis()+"_"+ originalFileName;
                    String savePath = "D:\\springboot_img\\" + storedFileName;
                    boardFile.transferTo(new File(savePath));

                    BoardFileEntity boardFileEntity =
                            BoardFileEntity.toSaveFileEntity(entity,originalFileName,storedFileName);  //부모데이터(게시글)조회를 해오는 작업 다시 넘겨주는 것
                    boardFileRepository.save(boardFileEntity);
                }


            return savedId;
        }
//        Long saveId = boardRepository.save(BoardEntity.toSaveEntity(boardDTO)).getId();
//        return saveId;
    }
    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
        List<BoardDTO> boardList = new ArrayList<>();
        for(BoardEntity boardEntity: boardEntityList){
            BoardDTO boardDTO = BoardDTO.toDTO(boardEntity);
            boardList.add(boardDTO);
        }
        return boardList;
    }

    @Transactional // 부모엔티티에서 자식엔티티를 직접 가져올 때 필요
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

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber()-1; // 페이지 -1 0인 상태라 1로 맞추기 위함
        final int pageLimit = 3;
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));// 보고싶은 페이지(0 ) ,한페이지 몇개 볼거냐 . 정렬
        Page<BoardDTO> boardList = boardEntities.map(
                // boardEntities에 담긴 boardEntity 객체를 board에 담아서
                // baordDTO 객체로 하나씩 옮겨 담는 과정
                board -> new BoardDTO(board.getId(),
                        board.getBoardWriter(),
                        board.getBoardTitle(),
                        board.getCreatedTime(),
                        board.getBoardHits()
                )

        );

        return boardList;
    }
    @Transactional
    public List<BoardDTO> search(String type, String q) {
//        List<BoardDTO> byBoardTitleContainingOrBoardWriterContainingOrderByIdDesc = boardRepository.findByBoardTitleContainingOrBoardWriterContainingOrderByIdDesc(type, q);
        List<BoardDTO> boardDTOList = new ArrayList<>(); // add 작업이 필요 하니까
        List<BoardEntity> boardEntityList = null;
        if(type.equals("boardWriter")){
            boardEntityList = boardRepository.findByBoardWriterContainingOrderByIdDesc(q);

        }else if (type.equals("boardTitle")){
            boardEntityList = boardRepository.findByBoardTitleContainingOrderByIdDesc(q);
        }else {
            boardEntityList = boardRepository.findByBoardTitleContainingOrBoardWriterContainingOrderByIdDesc(q,q);
        }

        for(BoardEntity boardEntity : boardEntityList){
            boardDTOList.add(BoardDTO.toDTO(boardEntity));
        }
        // 작성자 검색
        // select * from board_table where board_writer like '%q%';
        return boardDTOList;

    }


//    public BoardDTO findByPass(String boardPassword) {
//        Optional<BoardEntity> optionalBoardEntity = boardRepository.findPass(boardPassword);
//        if (optionalBoardEntity.isPresent()){
//            return BoardDTO.toDTO(optionalBoardEntity.get());
//        }else {
//            return null;
//        }
//    }


//   public void updateCount(Long id ,BoardDTO boardDTO) {
//      Board board = boardRepository.findById(id)
//   }
}
