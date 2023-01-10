package com.board.service;

import com.board.domain.Board;
import com.board.repository.BoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//비즈니스 로직 작성
@Service
public class BoardServiceImpl implements BoardService{


    private BoardMapper boardMapper;
    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    @Override
    public int saveBoard(Board board) {
        return boardMapper.saveBoard(board);
    }

    @Override
    public Object getBoardList() {
        System.out.println("boardMapper.getBoardList() : " + boardMapper.getBoardList());
        List<Board> boardList = boardMapper.getBoardList();
        if(boardList.size() == 0 ){
            return "게시글없음";
        } else {
            return boardList;
        }
    }

    @Override
    public Object getBoardOne(Long idx) {
        Board board = boardMapper.getBoardOne(idx);
        if (board == null){
            return "게시글 없음";
        } else {
            return board;
        }
    }

    @Override
    public Optional<List<Board>> findBoardByTitle(String title) {
        Optional<List<Board>> result = Optional.ofNullable(boardMapper.findBoardByTitle(title));

      /*  if (result.get().size() > 0) {
            return result.get();
        } else {
            return "검색결과없음";
        }*/
        return result;
    }

    @Override
    public Optional<List<Board>> findBoardByName(String name) {
        Optional<List<Board>> result = Optional.ofNullable(boardMapper.findBoardByName(name));

        /*  if (result.get().size() > 0) {
            return result.get();
        } else {
            return "검색결과없음";
        }*/

        return result;
    }

    @Override
    public Long modifyBoard(Long idx) {
        return boardMapper.modifyBoard(idx);
    }

    @Override
    public int deleteBoard(Long idx) {
        return boardMapper.deleteBoard(idx);
    }

}
