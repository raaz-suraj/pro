package com.blogapi11.service;

import com.blogapi11.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId,CommentDto dto);
    CommentDto findCommentById(long id);
    List<CommentDto> findAllComment(long postId);
    CommentDto updateComment(long postId,long id,CommentDto dto);
    String deleteComment(long postId,long id);
}
