package com.conatuseus.book.springboot.service.posts;

import com.conatuseus.book.springboot.domain.posts.PostsRepository;
import com.conatuseus.book.springboot.web.dto.PostsSaveRequestDto;
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
}
