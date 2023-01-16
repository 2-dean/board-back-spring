package com.board.controller;

import com.board.domain.Board;
import com.board.service.AttachedFileService;
import com.board.service.BoardService;
import com.board.service.CommentService;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final int pageSize = 5;

    //게시글 전체 조회
    @GetMapping
    public ResponseEntity<PageInfo> selectCityList(@RequestParam(defaultValue = "1") Integer pageNum){
        try{
            //log.info("pageNum = {}, pageSize={}", pageNum, pageSize);
            PageInfo<Board> list = boardService.getBoardList(pageNum, pageSize);
            //log.info("City List size = {}", list);
            return ResponseEntity.ok(list);
        }catch (Exception e){
            //log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    //게시글 작성
    @PostMapping("/new")
    public int newBoardFile(Board board, @RequestParam MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Long fileIdx = attachedFileService.saveFile(file);
            board.setFileIdx(fileIdx);
        }
        return boardService.saveBoard(board);
    }

    @GetMapping(value="/{idx}/download",  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    public Object downLoadFile (@PathVariable("idx") Long idx) {
        Board board = (Board)boardService.getBoardOne(idx);
        if (board != null && board.getFileIdx() != null) {
            return attachedFileService.downloadFile(board.getFileIdx());
        } else {
            return "첨부파일 없음";
        }

    }

    //게시글과 댓글 조회
    @GetMapping("/{idx}")
    public Object getBoardOne(@PathVariable("idx") Long idx ) {
        Map<String, Object> boardAndReply = new HashMap<>();
        boardAndReply.put("Board", boardService.getBoardOne(idx));
        boardAndReply.put("Comment", commentService.getComment((idx)));

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
