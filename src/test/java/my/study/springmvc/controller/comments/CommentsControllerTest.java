package my.study.springmvc.controller.comments;

import my.study.springmvc.controller.comments.dto.CommentWritingRequest;
import my.study.springmvc.model.comments.Comment;
import my.study.springmvc.model.comments.CommentsRepository;
import my.study.springmvc.model.posts.Post;
import my.study.springmvc.model.posts.PostRepository;
import my.study.springmvc.testconfig.AbstractRestControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

class CommentsControllerTest extends AbstractRestControllerTest {
    private Post existsPost = null;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @BeforeEach
    void setUp() {
        List<Comment> allComments = commentsRepository.findAll();
        List<Comment> replies = allComments.stream().filter(comment -> comment.getParentId() != null).toList();
        List<Comment> comments = allComments.stream().filter(comment -> comment.getParentId() == null).toList();
        commentsRepository.deleteAll(replies);
        commentsRepository.deleteAll(comments);
    }

    private Post getExistsPost() {
        if (existsPost == null) {
            existsPost = postRepository.save(Post.builder()
                    .title("title")
                    .content("content")
                    .build());
        }

        return existsPost;
    }

    @DisplayName("댓글 목록 조회")
    @Test
    void getComments() {
        // given
        Comment comment = commentsRepository.save(Comment.builder()
                .postId(getExistsPost().getId())
                .content("comment")
                .build()
        );

        Comment reply = commentsRepository.save(Comment.builder()
                .postId(getExistsPost().getId())
                .content("reply")
                .parentId(comment.getId())
                .build()
        );

        // when/then
        webTestClient.get()
                .uri("posts/{postId}/comments", getExistsPost().getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo("success")
                .jsonPath("$.data").exists()
                .jsonPath("$.data.content").exists()
                .jsonPath("$.data.content[0].id").isEqualTo(comment.getId())
                .jsonPath("$.data.content[0].parentId").doesNotExist()
                .jsonPath("$.data.content[0].replies[0].id").isEqualTo(reply.getId())
                .jsonPath("$.data.content[0].replies[0].parentId").isEqualTo(reply.getParentId())
                .jsonPath("$.data.totalElements").isEqualTo(1)
        ;
    }

    @DisplayName("댓글 작성")
    @Test
    void writerComment() {
        // given
        CommentWritingRequest request = new CommentWritingRequest("comment", null);

        // when/then
        webTestClient.post()
                .uri("posts/{postId}/comments", getExistsPost().getId())
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo("success")
                .jsonPath("$.data").exists()
                .jsonPath("$.data.id").exists()
                .jsonPath("$.data.content").isEqualTo(request.content())
                .jsonPath("$.data.parentId").doesNotExist()
                .jsonPath("$.data.deleted").isEqualTo(false)
        ;
    }

    @DisplayName("대댓글 작성")
    @Test
    void writerReply() {
        // given
        Comment comment = commentsRepository.save(Comment.builder()
                .postId(getExistsPost().getId())
                .content("comment")
                .build());
        CommentWritingRequest request = new CommentWritingRequest("comment", comment.getId());

        // when/then
        webTestClient.post()
                .uri("posts/{postId}/comments", getExistsPost().getId())
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo("success")
                .jsonPath("$.data").exists()
                .jsonPath("$.data.id").exists()
                .jsonPath("$.data.content").isEqualTo(request.content())
                .jsonPath("$.data.parentId").isEqualTo(request.parentCommentId())
                .jsonPath("$.data.deleted").isEqualTo(false)
        ;
    }

    @DisplayName("댓글 삭제")
    @Test
    void deleteComment() {
        // given
        Comment comment = commentsRepository.save(Comment.builder()
                .postId(getExistsPost().getId())
                .content("comment")
                .build());

        // when/then
        webTestClient.delete()
                .uri("posts/{postId}/comments/{commentId}", getExistsPost().getId(), comment.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.result").isEqualTo("success")
        ;
    }
}
