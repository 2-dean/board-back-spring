package com.board.service;

import com.board.domain.Comment;
import com.board.domain.Comment;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface CommentService {
    //게시글에 달린 댓글
    List<Comment> getComments(Long boardIdx);
    PageInfo<Comment> getComment(Long boardIdx, int pageNum, int pageSize);

    //댓글 작성
    int newComment(Comment comment);

    //댓글 수정
    int modifyComment(Comment modifyComent);

    //댓글 삭제
    int deleteComment(Map<String, Long> deleteCommentIdx);

    //댓글 삭제(내 댓글)
    int deleteMyComment(Map<String, Object> deleteMyComment);

}
