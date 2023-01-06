package com.board;

import com.board.domain.Board;
import com.board.service.BoardService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TestboardApplicationTests {

    @Autowired
    BoardService boardService;

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
        Board result = boardService.getBoardOne(bnum);
        System.out.println("getBoardOne : " + result.toString());
    }


}
