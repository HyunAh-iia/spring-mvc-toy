package my.study.springmvc.services.posts;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.model.posts.Post;
import my.study.springmvc.model.posts.PostRepository;
import my.study.springmvc.model.posts.exception.PostNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private static final boolean NOT_DELETED = false;

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostList(Pageable pageable) {
        return postRepository.findAllByDeleted(NOT_DELETED, pageable);
    }

    @Transactional
    public Post writerPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, Post newPost) {
        Post post = getPost(id);
        return post.update(newPost);
    }

    @Transactional
    public void deletePost(Long id) {
        getPost(id).delete();
    }
}
