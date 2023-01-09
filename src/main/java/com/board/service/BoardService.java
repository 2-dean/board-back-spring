package com.board.service;

import com.board.domain.Board;

import java.util.List;
import java.util.Optional;


public interface BoardService {
    int save(Board board);
    List<Board> getBoardList();
    Board getBoardOne(int bnum);

    //Optional<List<Board>> findBoardByTitle(String title);
    List<Board> findBoardByTitle(String title);
    List<Board> findBoardByWriter(String name);

    int modifyBoard(Board board);

    int deleteBoard(Board board);
}
