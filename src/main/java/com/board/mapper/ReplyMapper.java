package com.board.mapper;

import com.board.domain.Reply;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReplyMapper {
    // 게시글에 달린 댓글
    List<Reply> getReplies(Long boardIdx);

    //댓글 작성
    int newReply(Reply reply);

    //댓글 수정
    int modifyReply(Map<String, Object> modifyReply);

    //댓글 삭제
//    int deleteReply(Long idx);
    int deleteReply(Map<String, Long> deleteReplyIdx);

    //댓글 삭제(내 댓글)
    int deleteMyReplies (Map<String, Object> deleteMyReplies);

}
