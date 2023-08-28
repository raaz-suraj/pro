package com.blogapi11.controller;

import com.blogapi11.payload.PostDto;
import com.blogapi11.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/post")
public class PostRestController {

    @Autowired
    private PostService postSevice;

 @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create") // http://localhost:8080/api/post/create
    public ResponseEntity<?> createPost(@RequestBody @Valid PostDto dto, BindingResult result){
        if(result.hasFieldErrors()){
            return new ResponseEntity<>
                    (result.getFieldErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto post = postSevice.createPost(dto);
        return new ResponseEntity<>(post,HttpStatus.CREATED);
    }

    @GetMapping("/getting/{id}")  // http://localhost:8080/api/post/getting/1
    public ResponseEntity<PostDto> getPostById( @PathVariable("id") long id){
        PostDto postById = postSevice.findPostById(id);
        return new ResponseEntity<>(postById,HttpStatus.OK);
    }

    @GetMapping("/all") // http://localhost:8080/api/post/all?pageNo=0&pageSize=3&sortBy=id&sortDir=asc
    public List<PostDto> getAllPost(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "3",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        List<PostDto> allPost = postSevice.findAllPost(pageNo, pageSize, sortBy, sortDir);
        return allPost;
    }

    @PutMapping("/update/{id}") // http://localhost:8080/api/post/update/1
    public  ResponseEntity<?> updatePost(@PathVariable("id") long id,@RequestBody @Valid PostDto dto,BindingResult result){
        if(result.hasFieldErrors()){
            return new ResponseEntity<>(result.getFieldErrors().stream()
                    .map(x->x.getDefaultMessage()).collect(Collectors.toList()),HttpStatus.BAD_REQUEST);
        }
        PostDto postDto = postSevice.updatePost(id, dto);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}") // http://localhost:8080/api/post/update/1
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postSevice.deletePost(id);
        return new ResponseEntity<>("post is deleted",HttpStatus.OK);
    }
}
