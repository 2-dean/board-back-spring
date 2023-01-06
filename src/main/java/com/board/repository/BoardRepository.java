package com.board.repository;

import com.board.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardRepository {
    //CRDU
    //게시글등록, 게시글 전체조회, 게시글 제목/이름 조회, 게시글 수정, 게시글 삭제
    int save(Board board);
    //    Optional<Board> getBoardList();
    List<Board> getBoardList();
    Board getBoardOne(int bnum);

    //골라서 둘 중 1개 하도록
    Optional<List<Board>> findBoardByTitle(String title);
    Optional<List<Board>> findBoardByWriter(String name);

    int modifyBoard(Board board);

    int deleteBoard(Board board);


}
