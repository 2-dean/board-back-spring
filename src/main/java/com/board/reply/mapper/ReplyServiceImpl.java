package com.board.reply.mapper;

import com.board.reply.domain.Reply;
import com.board.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;

    @Override
    public int saveReply(Reply reply) {
        return replyMapper.saveReply(reply);
    }

    @Override
    public List<Reply> getMyReplies(Map<String, Object> myReplies) {
        return replyMapper.getMyReplys(myReplies);
    }

    @Override
    public int modifyReply(Map<String, Object> modifyReply) {
        return replyMapper.modifyReply(modifyReply);
    }

    @Override
    public int deleteReply(Long idx) {
        return replyMapper.deleteReply(idx);
    }

    @Override
    public int deleteMyReplies(Map<String, Object> deleteMyReplies) {
        return replyMapper.deleteMyReplies(deleteMyReplies);
    }
}
