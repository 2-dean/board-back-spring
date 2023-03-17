package com.board.mapper;

import com.board.domain.Board;
import com.board.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    //게시글 작성
    int saveBoard(Board board);

    //게시글 전체 목록 불러오기
    List<Board> getBoardList();

    //게시글 번호로 1개 게시글 가져오기
    BoardVO getBoardOne(Long idx);

    //검색
    List<Board> findBoardByTitle(String title);
    List<Board> findBoardByName(String name);

    //게시글 수정
    int modifyBoard(Map<String, Object> modifyBoard);

    //게시글 삭제
    int deleteBoard(Long idx);


}
