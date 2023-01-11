package com.board.board.service;

import com.board.board.domain.Board;
import com.board.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//비즈니스 로직 작성
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

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
        return result;
    }

    @Override
    public Optional<List<Board>> findBoardByName(String name) {
        Optional<List<Board>> result = Optional.ofNullable(boardMapper.findBoardByName(name));
        return result;
    }

    @Override
    public int modifyBoard(Map<String, Object> modifyBoard) {
        return boardMapper.modifyBoard(modifyBoard);
    }

    @Override
    public int deleteBoard(Long idx) {
        return boardMapper.deleteBoard(idx);
    }

}
