package com.board.service;

import com.board.domain.Reply;
import com.board.mapper.ReplyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;

    @Override
    public List<Reply> getReplies(Long boardIdx) {
        return replyMapper.getReplies(boardIdx);
    }

    @Override
    public int newReply(Reply reply) {
        return replyMapper.newReply(reply);
    }

    @Override
    public int modifyReply(Map<String, Object> modifyReply) {
        return replyMapper.modifyReply(modifyReply);
    }


   /* public int deleteReply(Long idx) {
        return replyMapper.deleteReply(idx);
    }*/
    @Override
    public int deleteReply(Map<String, Long> deleteReplyIdx) {
        return replyMapper.deleteReply(deleteReplyIdx);
    }

    @Override
    public int deleteMyReplies(Map<String, Object> deleteMyReplies) {
        return replyMapper.deleteMyReplies(deleteMyReplies);
    }
}
