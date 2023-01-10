package com.board.controller;

import com.board.domain.Board;
import com.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RequestMapping("boards")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

 /*   @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }*/

    @PostMapping("/save")
    public int saveBoard(@RequestBody Board board) {
        int result = boardService.saveBoard(board);
        return result;
    }

    @GetMapping(value = "/all")
    public Object getBoardList(){
      return boardService.getBoardList();
    }
    @GetMapping("/{idx}")
    public Object getBoardOne(@PathVariable("idx") Long idx ) {
        return boardService.getBoardOne(idx);
    }
    @GetMapping("/title/")
    public Object findBoardByTitle(@RequestParam("title") String title) {
        return boardService.findBoardByTitle(title);
    }

    @GetMapping("/findBoardByWriter")
    public Object findBoardByName(@RequestParam("name") String name) {
          return boardService.findBoardByName(name);
    }


  /*  @PostMapping("/deleteBoard")
    public int deleteBoard(@RequestBody Board board) {
        int result = boardService.deleteBoard(board);
        return result;
    }

    @PatchMapping("/{idx}")
    public int modifyBoard( @PathVariable("idx") int idx) {
        return boardService.modifyBoard(board);
    }*/



}
