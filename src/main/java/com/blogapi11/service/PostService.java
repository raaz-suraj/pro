package com.blogapi11.service;

import com.blogapi11.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto dto);
    PostDto findPostById(long id);
    List<PostDto> findAllPost(int pageNo,int pageSize,String sortBy,String sortDir);
    PostDto updatePost(long id,PostDto dto);
    String deletePost(long id);
}
