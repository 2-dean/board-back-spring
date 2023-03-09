package com.board.service.impl;

import com.board.domain.Comment;
import com.board.mapper.CommentMapper;
import com.board.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    public List<Comment> getComments(Long boardIdx) {
        return commentMapper.getComment(boardIdx);
    }

    @Override
    public PageInfo<Comment> getComment(Long boardIdx, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return PageInfo.of(commentMapper.getComment(boardIdx));
    }

    @Override
    public int newComment(Comment comment) {
        return commentMapper.newComment(comment);
    }

    @Override
    public int modifyComment(Comment modifyComent) {
        return commentMapper.modifyComment(modifyComent);
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
