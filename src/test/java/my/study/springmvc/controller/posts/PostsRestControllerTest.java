package my.study.springmvc.controller.posts;

import my.study.springmvc.model.posts.Post;
import my.study.springmvc.model.posts.PostRepository;
import my.study.springmvc.testconfig.AbstractRestControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.UUID;

class PostsRestControllerTest extends AbstractRestControllerTest {
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    @DisplayName("게시판 조회")
    @Test
    void getPost() {
        // given
        Post savedPost = postRepository.save(Post.builder()
                .title("title")
                .content(UUID.randomUUID().toString())
                .build());

        // when/then
        webTestClient
                .get().uri("/posts/" + savedPost.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo("success")
                .jsonPath("$.data").exists()
                .jsonPath("$.data.id").isEqualTo(savedPost.getId())
                .jsonPath("$.data.title").isEqualTo(savedPost.getTitle())
                .jsonPath("$.data.content").isEqualTo(savedPost.getContent())
                .jsonPath("$.data.fileLinks").exists()
                .jsonPath("$.data.createdAt").isNotEmpty()
                .jsonPath("$.data.updatedAt").isNotEmpty()
        ;
    }

    @DisplayName("존재하지 않는 게시글 조회 시 예외 발생")
    @Test
    void throwPostNotFoundWhenGetPost() {
        // when/then
        webTestClient
                .get().uri("/posts/" + 0L)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.result").isEqualTo("failure")
                .jsonPath("$.reason").isEqualTo("Post not found")
                .jsonPath("$.traceId").exists()
        ;
    }

    @DisplayName("게시판 목록 조회")
    @Test
    void getPostList() {
        // given
        postRepository.save(Post.builder()
                .title("title")
                .content(UUID.randomUUID().toString())
                .build());

        // when/then
        webTestClient
                .get().uri("/posts")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo("success")
                .jsonPath("$.data").exists()
                .jsonPath("$.data.content").exists()
        ;
    }

    @DisplayName("게시판 작성")
    @Test
    void writePost() {
        // given
        final String title = "title";
        final String content = UUID.randomUUID().toString();
        final MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("title", title);
        multipartBodyBuilder.part("content", content);
        multipartBodyBuilder.part("files", new ClassPathResource("/files/test.jpeg"));
        final MultiValueMap<String, HttpEntity<?>> requestParts = multipartBodyBuilder.build();

        // when/then
        webTestClient
                .post().uri("/posts")
                .headers(headers -> headers.setContentType(MediaType.MULTIPART_FORM_DATA))
                .body(BodyInserters.fromMultipartData(requestParts))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo("success")
                .jsonPath("$.data").exists()
                .jsonPath("$.data.title").isEqualTo(title)
                .jsonPath("$.data.content").isEqualTo(content)
                .jsonPath("$.data.fileLinks").exists()
                .jsonPath("$.data.createdAt").isNotEmpty()
                .jsonPath("$.data.updatedAt").isNotEmpty()
        ;
    }

    @DisplayName("게시판 수정")
    @Test
    void updatePost() {
        // given
        final String title = "title";
        final String content = UUID.randomUUID().toString();
        final String newTitle = "newTitle";
        final String newContent = UUID.randomUUID().toString();

        Post savedPost = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        final MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("title", newTitle);
        multipartBodyBuilder.part("content", newContent);
        multipartBodyBuilder.part("files", new ClassPathResource("/files/test.jpeg"));
        final MultiValueMap<String, HttpEntity<?>> requestParts = multipartBodyBuilder.build();

        // when/then
        webTestClient
                .put().uri("/posts/" + savedPost.getId())
                .headers(headers -> headers.setContentType(MediaType.MULTIPART_FORM_DATA))
                .body(BodyInserters.fromMultipartData(requestParts))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo("success")
                .jsonPath("$.data").exists()
                .jsonPath("$.data.title").isEqualTo(newTitle)
                .jsonPath("$.data.content").isEqualTo(newContent)
                .jsonPath("$.data.fileLinks").exists()
                .jsonPath("$.data.createdAt").isNotEmpty()
                .jsonPath("$.data.updatedAt").isNotEmpty()
        ;
    }

    @DisplayName("게시판 삭제")
    @Test
    void deletePost() {
        // given
        final String title = "title";
        final String content = UUID.randomUUID().toString();

        Post savedPost = postRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        // when/then
        webTestClient
                .delete().uri("/posts/" + savedPost.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo("success")
        ;
    }
}