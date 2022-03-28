package my.study.springmvc.controller;

import my.study.springmvc.controller.posts.dto.PostWritingRequest;
import my.study.springmvc.testconfig.AbstractRestControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RestControllerExceptionHandlerTest extends AbstractRestControllerTest {

    @DisplayName("사용자정의 예외 테스트")
    @Test
    void throwToyApplicationException() {
        webTestClient
                .get()
                .uri("/posts/999")
                .header("Accept-Language", "ko")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("$.result").isEqualTo("failure")
                .jsonPath("$.reason").isEqualTo("존재하지 않는 게시글입니다.");
    }

    @DisplayName("MethodArgumentNotValidException 예외를 처리하는 테스트")
    @Test
    void throwMethodArgumentNotValidException() {
        // given
        final PostWritingRequest request = new PostWritingRequest("title", "");

        // when/then
        webTestClient
                .post()
                .uri("/posts")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody()
                .jsonPath("$.result").isEqualTo("failure")
                .jsonPath("$.reason").exists();
    }
}