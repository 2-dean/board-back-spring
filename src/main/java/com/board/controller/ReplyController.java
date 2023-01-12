package com.board.controller;

import com.board.domain.Reply;
import com.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/boards/{boardIdx}/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 작성
    @PostMapping("/new")
    public int newReply(@PathVariable Long boardIdx, @RequestBody Reply reply) {
        reply.setBoardIdx(boardIdx);
        return replyService.newReply(reply);
    }


    //댓글 수정
    @PostMapping("/{idx}")
    int modifyReply(@PathVariable Long boardIdx, @PathVariable Long idx,
                    @RequestBody Map<String, Object> modifyReply) {

        modifyReply.put("boardIdx", boardIdx);
        modifyReply.put("idx", idx);

        return replyService.modifyReply(modifyReply);
    }

    //댓글 삭제
    @DeleteMapping("/{idx}")
    int deleteReply(@PathVariable Long boardIdx, @PathVariable Long idx) {
        Map<String, Long> deleteReplyIdx = new HashMap<>();
        deleteReplyIdx.put("boardIdx", boardIdx);
        deleteReplyIdx.put("idx", idx);

        return replyService.deleteReply(deleteReplyIdx);
    }

   //내 댓글 삭제
    @DeleteMapping("/name/{name}")
    int deleteMyReplies (@PathVariable Long boardIdx, @PathVariable String name) {
        Map<String, Object> myReplies = new HashMap<>();
        myReplies.put("boardIdx", boardIdx);
        myReplies.put("name", name);
        return replyService.deleteMyReplies(myReplies);
    }

}
