package com.its.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    //자식 엔티티에서는 자기를 기준으로 부모 엔티티와 어떤 관계인지
    // 게시글: 첨부파일 =1 : n
    // 첨부파일과 게시글은 n : 1  기준에 따라 변경
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // 테이블에 생성될 pk 컬럼 이름
    private BoardEntity boardEntity; // 부모 엔티티 타입의 필드가 와야함


    public static BoardFileEntity toSaveFileEntity(BoardEntity entity, String originalFileName, String storedFileName) { // 매개변수로 값만 담아주면 되기 때문에 굳이 필드처리하지 않고 매개변수로 처리
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setStoredFileName(storedFileName);
        boardFileEntity.setBoardEntity(entity);
        return boardFileEntity;
    }
}
