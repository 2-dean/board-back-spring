package com.board.controller;

import com.board.domain.Board;
import com.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    //롬복으로 하세요
    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = "/getBoardList")
    public Object getBoardList(){
        List<Board> boardList = boardService.getBoardList();
        if(boardList.size() == 0 ){
            return "게시글없음";
        } else {
            return boardList;
        }
    }

    @GetMapping("/getBoardOne")
    public Object getBoardOne(@RequestParam("bnum") int bnum ) {
        Board board = boardService.getBoardOne(bnum);
        if (board == null){
            return "게시글 없음";
        } else {
            return board;
        }
    }
    @GetMapping("/findBoardByTitle")
    public Object findBoardByTitle(@RequestParam("title") String title) {
        Optional<List<Board>> result = Optional.ofNullable(boardService.findBoardByTitle(title));
        if (result.get().size() > 0) {
            return result.get();
        } else {
            return "검색결과없음";
        }
    }
    @GetMapping("/findBoardByWriter")
    public Object findBoardByWriter(@RequestParam("name") String name) {
        Optional<List<Board>> result = Optional.ofNullable(boardService.findBoardByWriter(name));
        if (result.get().size() > 0) {
            return result.get();
        } else {
            return "검색결과없음";
        }
    }


    @PostMapping("/save")
    public int saveBoard(@RequestBody Board board) {
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
