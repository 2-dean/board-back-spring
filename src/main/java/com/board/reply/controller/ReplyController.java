package com.board.reply.controller;

import com.board.reply.domain.Reply;
import com.board.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    // 댓글 작성
    public int saveReply(Reply reply) {
        return replyService.saveReply(reply);
    }
    //게시글 중 내가 작성한 댓글
    public List<Reply> getMyReplies(Map<String, Object> myReplies) {
        return replyService.getMyReplies(myReplies);
    }

    //댓글 수정
    int modifyReply(Map<String, Object> modifyReply) {
        return replyService.modifyReply(modifyReply);
    }

    //댓글 삭제
    int deleteReply(Long idx) {
        return replyService.deleteReply(idx);
    }

    //댓글 삭제(내 댓글)
    int deleteMyReplies (Map<String, Object> deleteMyReplies) {
        return replyService.deleteMyReplies(deleteMyReplies);
    }
}
