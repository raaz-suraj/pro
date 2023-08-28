package com.blogapi11.service.impl;

import com.blogapi11.entity.Post;
import com.blogapi11.exception.ResourceNotFoundException;
import com.blogapi11.payload.PostDto;
import com.blogapi11.repository.PostRepository;
import com.blogapi11.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private ModelMapper modelMapper;

    PostDto mapToDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }
    Post mapToEntity(PostDto dto){
       return modelMapper.map(dto,Post.class);
    }
    @Override
    public PostDto createPost(PostDto dto) {
        Post post = mapToEntity(dto);
        Post save = postRepo.save(post);
        PostDto postDto = mapToDto(save);
        return postDto;
    }

    @Override
    public PostDto findPostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public List<PostDto> findAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
         Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                 Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
       Pageable pageable =PageRequest.of(pageNo,pageSize,sort);
        Page<Post> all = postRepo.findAll(pageable);
        List<Post> content = all.getContent();
        List<PostDto> collect = content.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public PostDto updatePost(long id, PostDto dto) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        Post post1 = mapToEntity(dto);
        post1.setId(post1.getId());
        Post save = postRepo.save(post1);
        PostDto postDto = mapToDto(save);
        return postDto;
    }

    @Override
    public String deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        postRepo.deleteById(id);
        return "post is deleted";
    }
}
