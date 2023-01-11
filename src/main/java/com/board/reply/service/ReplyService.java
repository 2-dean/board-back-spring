package com.board.reply.service;

import com.board.reply.domain.Reply;

import java.util.List;
import java.util.Map;

public interface ReplyService {
    //댓글 작성
    int saveReply(Reply reply);

    //게시글 중 내가 작성한 댓글
    List<Reply> getMyReplies(Map<String, Object> myReplies);

    //댓글 수정
    int modifyReply(Map<String, Object> modifyReply);

    //댓글 삭제
    int deleteReply(Long idx);

    //댓글 삭제(내 댓글)
    int deleteMyReplies (Map<String, Object> deleteMyReplies);

}
