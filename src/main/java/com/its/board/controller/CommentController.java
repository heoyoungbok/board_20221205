package com.its.board.controller;

import com.its.board.dto.CommentDTO;
import com.its.board.service.BoardService;
import com.its.board.service.CommentService;
import lombok.RequiredArgsConstructor;
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
    private final BoardService boardService;

    @PostMapping ("/save/{id}")
    public @ResponseBody List<CommentDTO> save(@PathVariable Long id, @ModelAttribute CommentDTO commentDTO) {
       commentService.save(commentDTO);
       List<CommentDTO> commentDTOList = commentService.findAll();
        commentService.findById(id);
        return commentDTOList;


    }


}

