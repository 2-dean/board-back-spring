package com.board.controller;

import com.board.domain.Comment;
import com.board.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Tag(name = "Comment", description = "댓글 api")
@RestController
@RequestMapping("/board/{boardIdx}/comment")
@RequiredArgsConstructor
public class CommentController{
    //게시글에 달린 댓글은 BoardController 에서 구현

    private final CommentService commentService;

    //댓글 작성
    @Operation(summary = "게시글에 댓글 작성")
    @PostMapping("/new")
    public int newComment(@PathVariable Long boardIdx, @RequestBody Comment comment) {
        comment.setBoardIdx(boardIdx);
        return commentService.newComment(comment);
    }

    //댓글 수정
    @Operation(summary = "댓글 수정")
    @PostMapping("/{idx}")
    int modify(@PathVariable Long idx, @RequestBody Comment comment) {
        comment.setIdx(idx);
        return commentService.modifyComment(comment);
    }

    //댓글 삭제
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{idx}")
    int delete(@PathVariable Long boardIdx, @PathVariable Long idx) {
        Map<String, Long> deleteIdx = new HashMap<>();
        deleteIdx.put("boardIdx", boardIdx);
        deleteIdx.put("idx", idx);

        return commentService.deleteComment(deleteIdx);
    }

   //내 댓글 삭제
    @Operation(summary = "작성자의 댓글 삭제")
    @DeleteMapping("/name/{name}")
    int deleteMycomment (@PathVariable Long boardIdx, @PathVariable String name) {
        Map<String, Object> myComment = new HashMap<>();
        myComment.put("boardIdx", boardIdx);
        myComment.put("name", name);
        return commentService.deleteMyComment(myComment);
    }

}
