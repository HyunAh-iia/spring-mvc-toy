package my.study.springmvc.model.posts;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostTest {
    @DisplayName("Post 생성 성공")
    @Test
    void testPostCreationSuccess() {
        // given
        final String title = "제목";
        final String content = "게시글 내용";
        final List<String> urls = List.of("http://url1", "http://url2");

        // when
        Post post = Post.builder()
                .title(title)
                .content(content)
                .fileUrls(urls)
                .build();

        // then
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getFileUrls()).containsExactlyElementsOf(urls);
    }

    @DisplayName("Post 생성 실패 - title invalid")
    @CsvSource({
            ",title can not be null or blank",
            "'',title can not be null or blank",
            "' ',title can not be null or blank"
    })
    @ParameterizedTest
    void testPostCreationFailureWhenTitleIsInvalid(final String title, final String expectedMessage) {
        // given
        final String content = "게시글 내용";
        final List<String> urls = List.of("http://url1", "http://url2");

        // when/then
        assertThatThrownBy(() -> Post.builder()
                .title(title)
                .content(content)
                .fileUrls(urls)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @DisplayName("Post 생성 실패 - content invalid")
    @CsvSource({
            ",content can not be null or blank",
            "'',content can not be null or blank",
            "' ',content can not be null or blank"
    })
    @ParameterizedTest
    void testPostCreationFailureWhenContentIsInvalid(final String content, final String expectedMessage) {
        // given
        final String title = "제목";
        final List<String> urls = List.of("http://url1", "http://url2");

        // when/then
        assertThatThrownBy(() -> Post.builder()
                .title(title)
                .content(content)
                .fileUrls(urls)
                .build())
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedMessage);
    }

    @DisplayName("Post 수정 성공")
    @Test
    void testPostModificationSuccess() {
        // given
        final String title = "제목";
        final String content = "게시글 내용";
        final List<String> urls = List.of("http://url1", "http://url2");

        final String newTitle = "new title";
        final String newContent = "new content";
        final List<String> deletedUrls = Collections.singletonList(urls.get(0));

        // when
        Post post = Post.builder()
                .title(title)
                .content(content)
                .fileUrls(urls)
                .build();

        post.update(newTitle, newContent, deletedUrls);

        // then
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
        assertThat(post.getFileUrls()).containsExactlyElementsOf(deletedUrls);
    }

    @DisplayName("Post 삭제 성공")
    @Test
    void testPostDeletingSuccess() {
        // given
        final String title = "제목";
        final String content = "게시글 내용";
        final List<String> urls = List.of("http://url1", "http://url2");

        // when
        Post post = Post.builder()
                .title(title)
                .content(content)
                .fileUrls(urls)
                .build();

        post.delete();

        // then
        assertThat(post).isNotNull();
        assertThat(post.isDeleted()).isTrue();
    }
}