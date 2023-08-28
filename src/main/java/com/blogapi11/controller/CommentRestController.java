package com.blogapi11.controller;

import com.blogapi11.payload.CommentDto;
import com.blogapi11.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/created/{postId}") // http://localhost:8080/api/comment/created/1
    public ResponseEntity<CommentDto>  createcomment(@PathVariable("postId") long postId, @RequestBody CommentDto dto){
        CommentDto comment = commentService.createComment(postId, dto);
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}") // http://localhost:8080/api/comment/get/1
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") long id){
        CommentDto commentById = commentService.findCommentById(id);
        return  new ResponseEntity<>(commentById,HttpStatus.OK);
    }

    @GetMapping("/{postId}")// http://localhost:8080/api/comment/1
    public List<CommentDto> findallComment(@PathVariable("postId") long postId){
        List<CommentDto> allComment = commentService.findAllComment(postId);
        return allComment;
    }

    @PutMapping("/{postId}/update/{id}")// http://localhost:8080/api/comment/1/update/1
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,@PathVariable("id") long id,@RequestBody CommentDto dto){
        CommentDto commentDto = commentService.updateComment(postId, id, dto);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/delete/{id}") // http://localhost:8080/api/comment/1/delete/1
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,@PathVariable("id") long id){
        commentService.deleteComment(postId, id);
        return new ResponseEntity<>("Comment is deleted",HttpStatus.OK);
    }
}
