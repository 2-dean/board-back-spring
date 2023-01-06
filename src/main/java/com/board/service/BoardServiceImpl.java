package com.board.service;

import com.board.domain.Board;
import com.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService{

    private BoardRepository boardRepository;
    @Autowired
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public int save(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public List<Board> getBoardList() {
        return boardRepository.getBoardList();
    }

    @Override
    public Board getBoardOne(int bnum) {
        return boardRepository.getBoardOne(bnum);
    }

    @Override
    public Optional<List<Board>> findBoardByTitle(String title) {
        return boardRepository.findBoardByTitle(title);
    }

    @Override
    public Optional<List<Board>> findBoardByWriter(String name) {
        return boardRepository.findBoardByWriter(name);
    }

    @Override
    public int modifyBoard(Board board) {
        return boardRepository.modifyBoard(board);
    }

    @Override
    public int deleteBoard(Board board) {
        return boardRepository.deleteBoard(board);
    }
}
