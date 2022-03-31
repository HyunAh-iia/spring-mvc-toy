package my.study.springmvc.model.posts;

import my.study.springmvc.testconfig.AbstractRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @DisplayName("Post 저장")
    @Test
    void testSave() {
        // given
        final Post post = Post.builder()
                .title("title")
                .content("content")
                .build();

        // when
        final Post savePost = postRepository.save(post);

        // then
        assertThat(savePost).isEqualTo(post);
        assertThat(savePost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savePost.getContent()).isEqualTo(post.getContent());
    }

    @DisplayName("Post 조회")
    @Test
    void testFindByIdAndDeleted() {
        // given
        final boolean NOT_DELETED = false;
        final Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        postRepository.save(post);

        // when
        Optional<Post> queriedPost = postRepository.findByIdAndDeleted(post.getId(), NOT_DELETED);

        // then
        assertThat(queriedPost.isEmpty()).isFalse();
        assertThat(queriedPost.get().getId()).isGreaterThan(0L);
    }

    @DisplayName("삭제된 Post는 조회되지 않음")
    @Test
    void testNothingFindByIdAndDeleted() {
        // given
        final boolean NOT_DELETED = false;
        final Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        post.delete();
        postRepository.save(post);

        // when
        Optional<Post> queriedPost = postRepository.findByIdAndDeleted(post.getId(), NOT_DELETED);

        // then
        assertThat(queriedPost.isEmpty()).isTrue();
    }

    @DisplayName("삭제되지 않은 Post 목록 조회")
    @Test
    void testFindAllByDeleted() {
        // given
        final boolean NOT_DELETED = false;
        final Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        postRepository.save(post);
        final Pageable pageable = PageRequest.of(1, 10);

        // when
        Page<Post> queriedPost = postRepository.findAllByDeleted(NOT_DELETED, pageable);

        // then
        assertThat(queriedPost.getTotalElements()).isEqualTo(1L);
    }
}