package my.study.springmvc.services.posts;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.model.posts.Post;
import my.study.springmvc.model.posts.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
    }
}
