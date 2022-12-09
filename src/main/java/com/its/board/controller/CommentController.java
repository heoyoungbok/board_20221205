package com.its.board.controller;

import com.its.board.dto.CommentDTO;
import com.its.board.service.BoardService;
import com.its.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
//    private final BoardService boardService;

    @PostMapping ("/save")
    public ResponseEntity save(@RequestBody CommentDTO commentDTO) {
       commentService.save(commentDTO);
       List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId()); // 목록을 가져와서
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK); // 목록을 뿌려준다


    }


}

