package com.board.service;

import com.board.domain.Board;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface BoardService {
    int saveBoard(Board board);
    Object getBoardList();
    Object getBoardOne(Long idx);

    //Optional<List<Board>> findBoardByTitle(String title);
    Optional<List<Board>> findBoardByTitle(String title);
    Optional<List<Board>> findBoardByName(String name);

    int modifyBoard(Map<String, Object> modifyBoard);

    int deleteBoard(Long idx);

}
