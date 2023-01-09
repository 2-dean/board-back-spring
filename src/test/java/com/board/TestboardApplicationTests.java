package com.board;

import com.board.domain.Board;
import com.board.service.BoardService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

//@Transactional
@SpringBootTest
class TestboardApplicationTests {

//    @BeforeEach
//    static void initAll() {
//        System.out.println("Test시작");
//    }

    @BeforeEach
    void init() {

    }



    @Autowired
    BoardService boardService;

    @Test
    void sava() {
        Board board = new Board();
        board.setTitle("제목");
        board.setContent("내용");
        board.setName("작성자");
        System.out.println("저장 : " + boardService.save(board));
    }

    @Test
    void getBoardList() {
        System.out.println(boardService);
        List<Board> list= boardService.getBoardList();
        System.out.println(list);

        Assertions.assertThat(list.size()).isGreaterThan(1);
    }

    @Test
    void getBoardOne() {
        int bnum = 2;
        Optional result =  Optional.ofNullable(boardService.getBoardOne(bnum));
        if (result.isPresent()){
            System.out.println(result.get());
        } else {
            System.out.println("결과없음");
        }

    }

    @Test
    void modifyBoard(){//TODO 내일 할 일~
        Board board = boardService.getBoardOne(7);
        board.setTitle("7번 제목수정합니다.");
        board.setName("7번 내용수정합니다 ㅋㄷㅋㄷ");
        System.out.println("board : " + board);
        System.out.println("result : "  + boardService.modifyBoard(board));
    }
    @Test
    void deleteBoard() {
        Board board = boardService.getBoardOne(7);
        int result = boardService.deleteBoard(board);
        System.out.println("삭제 결과 : " + result);
    }

    @AfterEach
    void tearDown() {
    }

    @AfterAll
    static void tearDownAll() {
    }


}
