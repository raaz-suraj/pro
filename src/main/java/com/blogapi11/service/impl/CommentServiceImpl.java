package com.blogapi11.service.impl;

import com.blogapi11.entity.Comment;
import com.blogapi11.entity.Post;
import com.blogapi11.exception.ResourceNotFoundException;
import com.blogapi11.payload.CommentDto;
import com.blogapi11.payload.PostDto;
import com.blogapi11.repository.CommentRepository;
import com.blogapi11.repository.PostRepository;
import com.blogapi11.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepository postRepo;
    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private ModelMapper modelMapper;

    CommentDto mapToDto(Comment comment){
        return modelMapper.map(comment,CommentDto.class);
    }
    Comment mapToEntity(CommentDto dto){
        return modelMapper.map(dto,Comment.class);
    }
    @Override
    public CommentDto createComment(long postId, CommentDto dto) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));
        Comment comment = mapToEntity(dto);
        comment.setPost(post);
        Comment save = commentRepo.save(comment);
        CommentDto commentDto = mapToDto(save);
        return commentDto;
    }

    @Override
    public CommentDto findCommentById(long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> findAllComment(long postId) {
         postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException(postId));
        List<Comment> byPostId = commentRepo.findByPostId(postId);
        List<CommentDto> collect = byPostId.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public CommentDto updateComment(long postId, long id, CommentDto dto) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        Comment comment1 = mapToEntity(dto);
        comment1.setId(comment.getId());
        comment1.setPost(post);
        Comment save = commentRepo.save(comment1);
        CommentDto commentDto = mapToDto(save);
        return commentDto;
    }

    @Override
    public String deleteComment(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException(postId));
        Comment comment = commentRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        commentRepo.deleteById(id);
        return "comment is deleted";
    }
}
