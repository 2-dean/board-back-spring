package com.board.controller;

import com.board.domain.Board;
import com.board.service.AttachedFileService;
import com.board.service.BoardService;
import com.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/boards")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final AttachedFileService attachedFileService;
    private final CommentService commentService;

    //게시글 전체 조회
    @GetMapping
    public Object getBoardList(){
        return boardService.getBoardList();
    }


    //게시글 작성
    @PostMapping("/new")
    public int newBoard(Board board) {
        int result = boardService.saveBoard(board);
        return result;
    }

    //게시글 작성 + 파일
    @PostMapping("/new-file")
    public int newBoardFile(Board board, @RequestParam MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            Long fileIdx = attachedFileService.saveFile(file);
            board.setFileIdx(fileIdx);
        }
        System.out.println("저장될 board : " + board);
        return boardService.saveBoard(board);
    }

    @RequestMapping("/{idx}/download")
    public int downLoadFile (@PathVariable("idx") Long idx) {
        Board board = (Board)boardService.getBoardOne(idx);
        Long fileIdx = board.getFileIdx();

        new FileSystemResource();
        return 0;
    }

    //게시글과 댓글 조회
    @GetMapping("/{idx}")
    public Object getBoardOne(@PathVariable("idx") Long idx ) {
        Map<String, Object> boardAndReply = new HashMap<>();
        boardAndReply.put("Board", boardService.getBoardOne(idx));
        boardAndReply.put("Reply", commentService.getComment((idx)));

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
