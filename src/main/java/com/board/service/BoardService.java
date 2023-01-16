package com.board.service;

import com.board.domain.Board;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface BoardService {
    int saveBoard(Board board);
    PageInfo<Board> getBoardList(int pageNo, int pageSize);
    Object getBoardOne(Long idx);

    //Optional<List<Board>> findBoardByTitle(String title);
    Optional<List<Board>> findBoardByTitle(String title);
    Optional<List<Board>> findBoardByName(String name);

    int modifyBoard(Map<String, Object> modifyBoard);

    int deleteBoard(Long idx);

}
