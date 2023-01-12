package com.board.controller;

import com.board.domain.Board;
import com.board.service.BoardService;
import com.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/boards")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    //게시글 전체 조회
    @GetMapping
    public Object getBoardList(){
        return boardService.getBoardList();
    }


    //게시글 작성
    @PostMapping("/new")
    public int newBoard(@RequestBody Board board) {
        int result = boardService.saveBoard(board);
        return result;
    }



    //게시글과 댓글 조회
    @GetMapping("/{idx}")
    public Object getBoardOne(@PathVariable("idx") Long idx ) {
        Map<String, Object> boardAndReply = new HashMap<>();
        boardAndReply.put("Board", boardService.getBoardOne(idx));
        boardAndReply.put("Reply", replyService.getReplies((idx)));

        return boardAndReply;
    }

    //검색 제목
    @GetMapping("title/{title}")
    public Object findBoardByTitle(@PathVariable String title) {
        return boardService.findBoardByTitle(title);
    }
    //검색 작성자
    @GetMapping("name/{name}")
    public Object findBoardByName(@PathVariable String name) {
          return boardService.findBoardByName(name);
    }

    //게시글 삭제
    @DeleteMapping("/{idx}")
    public int deleteBoard(@PathVariable Long idx) {
        int result = boardService.deleteBoard(idx);
        return result;
    }

    //게시글 수정
    @PatchMapping("/{idx}")
    public int modifyBoard(@PathVariable("idx") Long idx, @RequestBody Map<String, Object>modifyBoard) {
         modifyBoard.put("idx", idx);
         return boardService.modifyBoard(modifyBoard);
    }



}
