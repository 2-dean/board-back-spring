package com.board.controller;

import com.board.domain.Comment;
import com.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/boards/{boardIdx}/comment")
@RequiredArgsConstructor
public class CommentController{

    private final CommentService commentService;

    //게시글에 달린 댓글은 BoardController 에서 구현

    // 댓글 작성
    @PostMapping("/new")
    public int newComment(@PathVariable Long boardIdx, @RequestBody Comment comment) {
        comment.setBoardIdx(boardIdx);
        return commentService.newComment(comment);
    }

    //댓글 수정
    @PostMapping("/{idx}")
    int modify(@PathVariable Long boardIdx, @PathVariable Long idx,
                    @RequestBody Map<String, Object> modify) {

        modify.put("boardIdx", boardIdx);
        modify.put("idx", idx);

        return commentService.modifyComment(modify);
    }

    //댓글 삭제
    @DeleteMapping("/{idx}")
    int delete(@PathVariable Long boardIdx, @PathVariable Long idx) {
        Map<String, Long> deleteIdx = new HashMap<>();
        deleteIdx.put("boardIdx", boardIdx);
        deleteIdx.put("idx", idx);

        return commentService.deleteComment(deleteIdx);
    }

   //내 댓글 삭제
    @DeleteMapping("/name/{name}")
    int deleteMycomment (@PathVariable Long boardIdx, @PathVariable String name) {
        Map<String, Object> myComment = new HashMap<>();
        myComment.put("boardIdx", boardIdx);
        myComment.put("name", name);
        return commentService.deleteMyComment(myComment);
    }

}
