package com.board.service;

import com.board.domain.Board;

import java.util.List;
import java.util.Optional;


public interface BoardService {
    int save(Board board);
    List<Board> getBoardList();
    Board getBoardOne(int bnum);
    //골라서 둘 중 1개 하도록
    Optional<List<Board>> findBoardByTitle(String title);
    Optional<List<Board>> findBoardByWriter(String name);

    int modifyBoard(Board board);

    int deleteBoard(Board board);
}
