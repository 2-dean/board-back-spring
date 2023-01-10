package com.board.service;

import com.board.domain.Board;

import java.util.List;
import java.util.Optional;


public interface BoardService {
    int saveBoard(Board board);
    Object getBoardList();
    Object getBoardOne(Long idx);

    //Optional<List<Board>> findBoardByTitle(String title);
    Optional<List<Board>> findBoardByTitle(String title);
    Optional<List<Board>> findBoardByName(String name);

    int modifyBoard(Long idx);

    int deleteBoard(Long idx);

    int modifyBoard(Board board);
}
