package com.conatuseus.book.springboot.web;

import com.conatuseus.book.springboot.service.posts.PostsService;
import com.conatuseus.book.springboot.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long savePosts(@RequestBody final PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }
}
