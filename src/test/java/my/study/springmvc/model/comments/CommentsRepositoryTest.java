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

import java.util.List;

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

    @DisplayName("댓글 저장")
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

    @DisplayName("댓글 목록 조회")
    @Test
    void findAllByPostId() {
        //given
        final Comment comment = Comment.builder()
                .postId(existsPostId)
                .content("comment")
                .build();
        commentsRepository.save(comment);
        final Pageable pageable = PageRequest.of(1, 1);

        // when
        Page<Comment> comments = commentsRepository.findAllByPostIdAndParentIdIsNull(existsPostId, pageable);

        // then
        assertThat(comments.getTotalElements()).isEqualTo(pageable.getPageSize());
    }

    @DisplayName("대댓글 목록 조회")
    @Test
    void findAllByParentIdIn() {
        // given
        final Comment comment = Comment.builder()
                .postId(existsPostId)
                .content("comment")
                .build();
        final Comment savedComment = commentsRepository.save(comment);

        final Comment reply = Comment.builder()
                .postId(existsPostId)
                .content("comment")
                .parentId(savedComment.getId())
                .build();
        commentsRepository.save(reply);

        // when
        List<Comment> replies = commentsRepository.findAllByParentIdIn(List.of(savedComment.getId()));

        assertThat(replies.size()).isEqualTo(1);
        assertThat(replies.get(0).getId()).isEqualTo(reply.getId());
        assertThat(replies.get(0).getParentId()).isEqualTo(savedComment.getId());
    }
}
