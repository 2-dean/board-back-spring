package com.board.service;

import com.board.domain.Reply;

import java.util.List;
import java.util.Map;

public interface ReplyService {
    // 게시글에 달린 댓글
    List<Reply> getReplies(Long boardIdx);

    //댓글 작성
    int newReply(Reply reply);

    //댓글 수정
    int modifyReply(Map<String, Object> modifyReply);

    //댓글 삭제
    int deleteReply(Map<String, Long> deleteReplyIdx);

    //댓글 삭제(내 댓글)
    int deleteMyReplies (Map<String, Object> deleteMyReplies);

    //TODO 230112 댓글페이징
    //List<Reply> getRepliesPage();
}
