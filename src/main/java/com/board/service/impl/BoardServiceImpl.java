package com.board.service.impl;

import com.board.domain.Board;
import com.board.mapper.BoardMapper;
import com.board.service.BoardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//비즈니스 로직 작성
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;


    @Override
    public int saveBoard(Board board) {
        return boardMapper.saveBoard(board);
    }


    @Override
    public PageInfo<Board> getBoardList(int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        return PageInfo.of(boardMapper.getBoardList());
    }

    @Override
    public List<Board> getBoardListAll() {
        return boardMapper.getBoardList();
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
        return Optional.ofNullable(boardMapper.findBoardByTitle(title));
    }

    @Override
    public Optional<List<Board>> findBoardByName(String name) {
        return Optional.ofNullable(boardMapper.findBoardByName(name));
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
