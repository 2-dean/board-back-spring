package com.board.board.controller;

import com.board.board.domain.Board;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/save")
    public int saveBoard(@RequestBody Board board) {
        int result = boardService.saveBoard(board);
        return result;
    }

    @GetMapping(value = "/boards")
    public Object getBoardList(){
      return boardService.getBoardList();
    }

    @GetMapping("/boards/{idx}")
    public Object getBoardOne(@PathVariable("idx") Long idx ) {
        return boardService.getBoardOne(idx);
    }

    @GetMapping("title/{title}")
    public Object findBoardByTitle(@PathVariable String title) {
        return boardService.findBoardByTitle(title);
    }

    @GetMapping("name/{name}")
    public Object findBoardByName(@PathVariable String name) {
          return boardService.findBoardByName(name);
    }


   @PostMapping("/{idx}")
    public int deleteBoard(@PathVariable Long idx) {
        int result = boardService.deleteBoard(idx);
        return result;
    }

    @PatchMapping("/{idx}")
    public int modifyBoard(@PathVariable("idx") Long idx, @RequestBody Map<String, Object>modifyBoard) {
         modifyBoard.put("idx", idx);
         return boardService.modifyBoard(modifyBoard);
    }



}
