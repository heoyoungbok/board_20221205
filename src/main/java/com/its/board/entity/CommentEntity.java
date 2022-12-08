package com.its.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20 , nullable = false)
    private String commentWriter;
    @Column(length = 200 , nullable = false)
    private String commentContents;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;
    public static CommentEntity toSaveComment(BoardEntity entity, String commentWriter, String commentContents){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentWriter);
        commentEntity.setCommentContents(commentContents);
        commentEntity.setBoardEntity(entity);
        return commentEntity;
    }
}
