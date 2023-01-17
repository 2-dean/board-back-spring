package com.board.mapper;

import com.board.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {
    // 게시글에 달린 댓글
    List<Comment> getComment(Long boardIdx);

    //댓글 작성
    int newComment(Comment Comment);

    //댓글 수정
    int modifyComment(Comment modifyComent);

    //댓글 삭제
    int deleteComment(Map<String, Long> deleteCommentIdx);

    //댓글 삭제(내 댓글)
    int deleteMyComment(Map<String, Object> deleteMyComment);

}
