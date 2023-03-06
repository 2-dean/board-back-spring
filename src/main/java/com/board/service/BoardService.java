package com.board.service;

import com.board.domain.Board;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface BoardService {

    //게시글 작성
    int saveBoard(Board board);

    //게시글 목록 (페이징)
    PageInfo<Board> getBoardList(int pageNo, int pageSize);
    List<Board> getBoardListAll();

    //게시글 1개 조회
    Object getBoardOne(Long idx);

    //제목으로 게시글 조회
    Optional<List<Board>> findBoardByTitle(String title);

    //이름으로 게시글 조회
    Optional<List<Board>> findBoardByName(String name);

    //게시글 수정
    int modifyBoard(Map<String, Object> modifyBoard);

    //게시글 삭제
    int deleteBoard(Long idx);

}
