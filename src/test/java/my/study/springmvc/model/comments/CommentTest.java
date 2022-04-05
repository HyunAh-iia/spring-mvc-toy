package my.study.springmvc.model.comments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommentTest {

    @DisplayName("Comment 생성")
    @Test
    void commentCreation() {
        // given
        final String content = "comment";
        final Long postId = 1L;

        // when
        final Comment comment = Comment.builder()
                .content(content)
                .postId(postId)
                .build();

        // then
        assertThat(comment).isNotNull();
        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getPostId()).isEqualTo(postId);
        assertThat(comment.isDeleted()).isFalse();
    }

    @DisplayName("Comment 삭제")
    @Test
    void commentDeleting() {
        // given
        final String content = "comment";
        final Long postId = 1L;
        final Comment comment = Comment.builder()
                .content(content)
                .postId(postId)
                .build();
        final String expectedContent = "deleted";

        // when
        comment.delete();

        // then
        assertThat(comment).isNotNull();
        assertThat(comment.getContent()).isEqualTo(expectedContent);
        assertThat(comment.getPostId()).isEqualTo(postId);
        assertThat(comment.isDeleted()).isTrue();
    }
}