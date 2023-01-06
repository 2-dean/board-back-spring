package com.board.controller;

import com.board.domain.Board;
import com.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "/getBoardList")
    public List<Board> getBoardList(){
        return boardService.getBoardList();
    }

    @PostMapping("/getBoardOne")
    public Object getBoardOne(@RequestBody Board board) {
        Board result = boardService.getBoardOne(board.getBnum());
        if (result == null){
            return "게시글 없음";
        } else {
            return result;
        }
    }
    @PostMapping("/save")
    public int save(@RequestBody Board board) {
        int result = boardService.save(board);
        return result;
    }

    @PostMapping("/deleteBoard")
    public int deleteBoard(@RequestBody Board board) {
        int result = boardService.deleteBoard(board);
        return result;
    }

    @PostMapping("/modifyBoard")
    public int modifyBoard(@RequestBody Board board) {
        return boardService.modifyBoard(board);
    }



}
