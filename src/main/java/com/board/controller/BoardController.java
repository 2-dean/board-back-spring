package com.board.controller;

import com.board.domain.AttachedFile;
import com.board.domain.Board;
import com.board.service.AttachedFileService;
import com.board.service.BoardService;
import com.board.service.CommentService;
import com.board.service.S3FileUploadService;
import com.board.dto.BoardDTO;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RequestMapping("/api/boards")
//@RequestMapping("/boards")
@Tag(name = "Board", description = "게시판 api")
@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final AttachedFileService attachedFileService;
    private final CommentService commentService;
    private final S3FileUploadService s3FileUploadService;
    //페이지당 수량
    private final int pageSize = 5;

    //게시글 전체 조회
    @Operation(summary = "게시판 전체 목록 조회", description = "front 에서 페이징 필요")
    @GetMapping("/boards")
    public ResponseEntity<List<Board>> getBoardListAll(){
        log.info("AllBoardList >> ");
        try{
            List<Board> list = boardService.getBoardListAll();
            System.out.println(list);
            return ResponseEntity.ok(list);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "게시글 1개와 댓글 조회", description = "front 에서 댓글 페이징 필요")
    @GetMapping("/board/{idx}")
    public Object getBoardAndComments(@PathVariable("idx") Long idx) {
        Map<String, Object> boardAndComment = new HashMap<>();
        boardAndComment.put("board", boardService.getBoardOne(idx));        // 게시글 정보
        boardAndComment.put("comment", commentService.getComments(idx));    // 게시글에 달린 댓글들

        return boardAndComment;
    }


    //게시글 전체 조회(페이지)
    @Operation(summary = "게시판 전체 목록 조회(백엔드페이징)", description = "pageNum 입력")
    @GetMapping("/boards/{pageNum}")
    public ResponseEntity<PageInfo> selectBoardList(@PathVariable("pageNum") Integer pageNum){
        log.info("selectBoardList >> ");
        try{
            PageInfo<Board> list = boardService.getBoardList(pageNum, pageSize);
            System.out.println(list);
            return ResponseEntity.ok(list);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //게시글 1개 조회
    @Operation(summary = "게시글 1개와 댓글 조회", description = "게시글 번호와 댓글 페이지 입력")
    @GetMapping("/board/{idx}/{pageNum}")
    public Object getBoardOne(@PathVariable("idx") Long idx, @PathVariable("pageNum") Integer pageNum ) {
        Map<String, Object> boardAndComment = new HashMap<>();
        boardAndComment.put("Board", boardService.getBoardOne(idx));
        boardAndComment.put("Comment", commentService.getComment(idx, pageNum, pageSize));

        return boardAndComment;
    }


    //게시글 작성 및 파일 업로드
    @Operation(summary = "게시글 작성 및 파일 업로드")
    @PostMapping(value = "/board/new", consumes = {"multipart/form-data"})
    public int newBoardFile(BoardDTO boardDTO, @RequestParam MultipartFile file) throws Exception {
        log.info("[ /board/new 받은 값 확인 ]");
        log.info("board : " + boardDTO.toString());
        log.info("file : " + file);
        Board board = new Board();
        board.setTitle(boardDTO.getTitle());
        board.setContent(boardDTO.getContent());
        board.setUserIdx(boardDTO.getUserIdx());

        if (!file.isEmpty()) {
            // AWS S3에 업로드
            AttachedFile attachedFile = s3FileUploadService.upload(file);
            // url 을 받아서 DB에 저장
            Long fileIdx = attachedFileService.saveFile(attachedFile);
            board.setFileIdx(fileIdx);
        }
        return boardService.saveBoard(board);
    }

    //파일다운로드
    @Operation(summary = "게시글의 파일 다운로드", description = "게시글 번호를 입력하면 파일이 다운로드 됩니다")
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
    @Operation(summary = "제목으로 검색")
    @GetMapping("/boards/title/{title}")
    public Object findBoardByTitle(@PathVariable String title) {
        return boardService.findBoardByTitle(title);
    }

    //검색 작성자
    @Operation(summary = "작성자로 검색")
    @GetMapping("/boards/name/{name}")
    public Object findBoardByName(@PathVariable String name) {
          return boardService.findBoardByName(name);
    }

    //게시글 삭제
    @Operation(summary = "게시글 삭제", description = "게시글 번호 입력시 데이터 삭제")
    @DeleteMapping("/board/{idx}")
    public int deleteBoard(@PathVariable Long idx) {
        int result = boardService.deleteBoard(idx);
        return result;
    }

    //게시글 수정
    @Operation(summary = "게시글 수정", description = "수정 데이터를 입력하면 수정됩니다")
    @PatchMapping("/board/{idx}")
    public int modifyBoard(@PathVariable("idx") Long idx, @RequestBody Map<String, Object>modifyBoard) {
         modifyBoard.put("idx", idx);
         return boardService.modifyBoard(modifyBoard);
    }



}
