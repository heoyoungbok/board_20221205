package com.its.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id;
    private List<String> commentWriter;
    private List<String>  commentContents;

}
