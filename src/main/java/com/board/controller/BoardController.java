package com.board.controller;

import com.board.domain.Board;
import com.board.service.AttachedFileService;
import com.board.service.BoardService;
import com.board.service.CommentService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@RequestMapping("/api/boards")
//@RequestMapping("/boards")
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final AttachedFileService attachedFileService;
    private final CommentService commentService;

    //페이지당 수량
    private final int pageSize = 5;

    //게시글 전체 조회(페이지)
    @Operation(summary = "조회", description = "게시글 전체 조회 api pageNum입력")
    @GetMapping("/boards/{pageNum}")
    public ResponseEntity<PageInfo> selectCityList(@PathVariable("pageNum") Integer pageNum){
        try{
            PageInfo<Board> list = boardService.getBoardList(pageNum, pageSize);
            System.out.println(list);
            return ResponseEntity.ok(list);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //게시글 1개 조회
    @GetMapping("/board/{idx}/{pageNum}")
    public Object getBoardOne(@PathVariable("idx") Long idx, @PathVariable("pageNum") Integer pageNum ) {
        Map<String, Object> boardAndComment = new HashMap<>();
        boardAndComment.put("Board", boardService.getBoardOne(idx));
        boardAndComment.put("Comment", commentService.getComment(idx, pageNum, pageSize));

        return boardAndComment;
    }

    //게시글 작성 및 파일 업로드
    @PostMapping("/board/new")
    public int newBoardFile(Board board, @RequestParam MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Long fileIdx = attachedFileService.saveFile(file);
            board.setFileIdx(fileIdx);
        }
        return boardService.saveBoard(board);
    }

    //파일다운로드
    @GetMapping(value="/board/{idx}/download",  produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    public Object downLoadFile (@PathVariable("idx") Long idx) throws FileNotFoundException {
        Board board = (Board)boardService.getBoardOne(idx);

        if (board != null && board.getFileIdx() != null) {
            return attachedFileService.downloadFile(board.getFileIdx());
        } else {
            return "첨부파일 없음";
        }

    }


    //검색 제목
    @GetMapping("/boards/title/{title}")
    public Object findBoardByTitle(@PathVariable String title) {
        return boardService.findBoardByTitle(title);
    }
    //검색 작성자
    @GetMapping("/boards/name/{name}")
    public Object findBoardByName(@PathVariable String name) {
          return boardService.findBoardByName(name);
    }

    //게시글 삭제
    @DeleteMapping("/board/{idx}")
    public int deleteBoard(@PathVariable Long idx) {
        int result = boardService.deleteBoard(idx);
        return result;
    }

    //게시글 수정
    @PatchMapping("/board/{idx}")
    public int modifyBoard(@PathVariable("idx") Long idx, @RequestBody Map<String, Object>modifyBoard) {
         modifyBoard.put("idx", idx);
         return boardService.modifyBoard(modifyBoard);
    }



}
