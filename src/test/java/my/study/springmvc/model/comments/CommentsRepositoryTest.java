package my.study.springmvc.model.comments;

import my.study.springmvc.model.posts.Post;
import my.study.springmvc.model.posts.PostRepository;
import my.study.springmvc.testconfig.AbstractRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

class CommentsRepositoryTest extends AbstractRepositoryTest {
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PostRepository postRepository;

    private Long existsPostId;

    @BeforeEach
    void setup() {
        commentsRepository.deleteAll();
        postRepository.deleteAll();

        final Post post = Post.builder()
                .title("title")
                .content("content")
                .build();

        existsPostId = postRepository.save(post).getId();
    }

    @DisplayName("Comment 저장")
    @Test
    void save() {
        // given
        final Comment comment = Comment.builder()
                .postId(existsPostId)
                .content("comment")
                .build();

        // when
        final Comment savedComment = commentsRepository.save(comment);

        // then
        assertThat(savedComment.getId()).isNotNull();
        assertThat(savedComment.getPostId()).isEqualTo(comment.getPostId());
        assertThat(savedComment.getContent()).isEqualTo(comment.getContent());
    }

    @DisplayName("Comment 목록 조회")
    @Test
    void findAllByPostId() {
        //given
        final Pageable pageable = PageRequest.of(1, 1);

        // when
        Page<Comment> comments = commentsRepository.findAllByPostId(existsPostId, pageable);

        // then
        assertThat(comments.getTotalElements()).isEqualTo(pageable.getPageSize());
    }
}