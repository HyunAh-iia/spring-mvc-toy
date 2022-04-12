package my.study.springmvc.model.comments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CommentTest {

    private static Stream<Arguments> throwExceptionWhenCommentCreationGivenInvalidRequest() {
        /*  [ test method arguments ]
            final Long postId,
            final String content,
            final Long parentId,
            final String expectedErrorMessage
        */
        final Long postId = 1L;
        final String content = "comment";
        final Long parentId = null;

        return Stream.of(
                arguments(null, content, parentId, "postId can not be null"),
                arguments(-1L, content, parentId, "postId must be greater than 0"),

                arguments(postId, null, parentId, "content can not be null or blank"),
                arguments(postId, "", parentId, "content can not be null or blank"),
                arguments(postId, " ", parentId, "content can not be null or blank"),

                arguments(postId, content, -1L, "parentId must be greater than 0")
        );
    }

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

    @DisplayName("유효하지 않은 입력 시 Comment 생성 실패")
    @MethodSource("throwExceptionWhenCommentCreationGivenInvalidRequest")
    @ParameterizedTest
    void throwExceptionWhenCommentCreationGivenInvalidRequest(final Long postId, final String content, final Long parentId, final String expectedErrorMessage) {
        // when/then
        assertThatThrownBy(() -> Comment.builder()
                .postId(postId)
                .content(content)
                .parentId(parentId)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedErrorMessage);
    }
}
