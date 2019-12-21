package com.conatuseus.book.springboot.service.posts;

import com.conatuseus.book.springboot.domain.posts.Posts;
import com.conatuseus.book.springboot.domain.posts.PostsRepository;
import com.conatuseus.book.springboot.web.dto.PostsResponseDto;
import com.conatuseus.book.springboot.web.dto.PostsSaveRequestDto;
import com.conatuseus.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(final PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity())
            .getId();
    }

    @Transactional
    public Long update(final Long id, final PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(final Long id) {
        Posts entity = postsRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));

        return new PostsResponseDto(entity);
    }
}
