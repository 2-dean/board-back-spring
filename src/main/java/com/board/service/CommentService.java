package com.board.service;

import com.board.domain.Comment;
import com.board.domain.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
    // 게시글에 달린 댓글
    List<Comment> getComment(Long boardIdx);

    //댓글 작성
    int newComment(Comment comment);

    //댓글 수정
    int modifyComment(Map<String, Object> modifyComment);

    //댓글 삭제
    int deleteComment(Map<String, Long> deleteCommentIdx);

    //댓글 삭제(내 댓글)
    int deleteMyComment(Map<String, Object> deleteMyComment);

    //TODO 230112 댓글페이징
    //List<Comment> getPageComment();
}
