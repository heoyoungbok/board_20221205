package com.its.board.controller;

import com.its.board.dto.BoardDTO;
import com.its.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String save(@ModelAttribute BoardDTO boardDTO)throws IOException {
        boardService.save(boardDTO);
        return "redirect:/board/";
    }

    @GetMapping("/")
    public String findAll(Model model){
        List<BoardDTO> boardList = boardService.findAll();
        model.addAttribute("boardList",boardList);
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



    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board",boardDTO);
        return "boardPages/boardUpdate";
    }
//    @GetMapping("/passCheck")
//    @ResponseBody private String getPassword(@RequestParam("pass")String boardPassword , Model model){
//       BoardDTO boardDTO = boardService.findByPass(boardPassword);
//        model.addAttribute("board",boardDTO);
//        return "/boardPages/passCheck";
//    }

        @PostMapping("/update") //오버로딩
        public String update(@ModelAttribute BoardDTO boardDTO,Model model){
        boardService.update(boardDTO);
       BoardDTO boardDTO1= boardService.findById(boardDTO.getId());
       model.addAttribute("board",boardDTO1);
       return "boardPages/boardDetail";
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

//    @PutMapping("/{id}")
//    public ResponseEntity update(@PathVariable Long id
//            ,@RequestBody BoardDTO boardDTO){
//     boardService.update(boardDTO);
//        return new ResponseEntity<>(HttpStatus.OK);
//
//
//    }


    @PutMapping("/{id}")   // 오버로딩
    public ResponseEntity updateAxios(@RequestBody BoardDTO boardDTO){
        boardService.update(boardDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public String deleteForm(@PathVariable Long id){
     boardService.delete(id);
     return "redirect:/board/";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAxios(@PathVariable Long id){
        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
