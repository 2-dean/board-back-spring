package com.board.service;

import com.board.domain.Comment;
import com.board.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public List<Comment> getComment(Long boardIdx) {
        return commentMapper.getComment(boardIdx);
    }

    @Override
    public int newComment(Comment comment) {
        return commentMapper.newComment(comment);
    }

    @Override
    public int modifyComment(Map<String, Object> modifyComment) {
        return commentMapper.modifyComment(modifyComment);
    }

    @Override
    public int deleteComment(Map<String, Long> deleteCommentIdx) {
        return commentMapper.deleteComment(deleteCommentIdx);
    }

    @Override
    public int deleteMyComment(Map<String, Object> deleteMyComment) {
        return commentMapper.deleteMyComment((deleteMyComment));
    }
}
