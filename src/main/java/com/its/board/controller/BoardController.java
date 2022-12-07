package com.its.board.controller;

import com.its.board.dto.BoardDTO;
import com.its.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/save")
    public String saveForm(){
        return "boardPages/boardSave";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);
        return "redirect:/board/";
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList",boardDTOList);
        return "boardPages/boardList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
//        boardService.updateCount(id);
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
//        Long countHits = (long) (boardDTO.getBoardHits() + +1);
//        model.addAttribute("board",boardService.updateCount(id));
//        boardService.updateHits(boardDTO.getId(),boardDTO);
        model.addAttribute("board",boardDTO);
        return "boardPages/boardDetail";
    }

    @GetMapping("/passCheck")
    @ResponseBody private String getPassword(@RequestParam("pass")String boardPassword , Model model){
       BoardDTO boardDTO = boardService.findByPass(boardPassword);
        model.addAttribute("board",boardDTO);
        return "/boardPages/passCheck";
    }



//    @GetMapping ("/{boardPassword}")
//    public ResponseEntity updateForm(@PathVariable String boardPassword){
//       BoardDTO boardDTO = boardService.findPass(boardPassword);
//        if (boardDTO != null){
//            return new ResponseEntity<>(boardDTO,HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id
            ,@RequestBody BoardDTO boardDTO){
     boardService.update(boardDTO);
        return new ResponseEntity<>(HttpStatus.OK);


    }




}
