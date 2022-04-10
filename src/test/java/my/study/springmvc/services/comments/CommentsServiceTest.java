package my.study.springmvc.services.comments;

import my.study.springmvc.controller.comments.dto.CommentRepliesDto;
import my.study.springmvc.model.comments.Comment;
import my.study.springmvc.model.comments.CommentsRepository;
import my.study.springmvc.model.posts.Post;
import my.study.springmvc.services.posts.PostsService;
import my.study.springmvc.testconfig.doubles.fake.FakeCommentsRepository;
import my.study.springmvc.testconfig.doubles.fake.FakePostRepository;
import my.study.springmvc.testconfig.doubles.stub.StubUploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

class CommentsServiceTest {
    private final CommentsRepository commentsRepository = new FakeCommentsRepository();
    private final PostsService postsService = new PostsService(new FakePostRepository(), new StubUploader());

    private CommentsService createdCommentsService() {
        return new CommentsService(commentsRepository, postsService);
    }

    private Post getExistsPost() {
        final int FIRST = 0;
        final Page<Post> postList = postsService.getPostList(PageRequest.of(FIRST, 1));
        if (postList.getTotalElements() == 0) {
            Post post = Post.builder()
                    .title("title")
                    .content("content")
                    .build();
            return postsService.writerPost(post, null);
        }
        return postList.getContent().get(FIRST);
    }

    @DisplayName("댓글 목록 조회")
    @Test
    void getComments() {
        // given
        final Long existsPostId = getExistsPost().getId();
        commentsRepository.save(Comment.builder()
                .postId(existsPostId)
                .content("content")
                .build());
        commentsRepository.save(Comment.builder()
                .postId(existsPostId)
                .content("content")
                .build());

        final Pageable pageable = PageRequest.of(0, 1);

        // when
        Page<CommentRepliesDto> commentList = createdCommentsService()
                .getCommentsWithReplies(existsPostId, pageable);

        // then
        assertThat(commentList.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("존재하지 않는 게시글의 댓글 목록 조회 시 예외 반환")
    @Test
    void throwExceptionWhenGetCommentsOfNotExistsPost() {
        // given
        final Long doesNotExistsPostId = -1L;

        // when/then
        assertThatThrownBy(() -> createdCommentsService()
                .getCommentsWithReplies(doesNotExistsPostId, PageRequest.of(0, 1)));
    }

    @DisplayName("댓글 작성")
    @Test
    void writeComment() {
        // given
        final Long existsPostId = getExistsPost().getId();
        final Long commentId = 5L;

        final Comment mockComment = Mockito.mock(Comment.class);
        given(mockComment.getId()).willReturn(commentId);
        given(mockComment.getPostId()).willReturn(existsPostId);
        given(mockComment.getContent()).willReturn("content");
        given(mockComment.getParentId()).willReturn(null);

        // when
        Comment savedComment = createdCommentsService().writeComment(mockComment);

        // then
        assertThat(savedComment.getId()).isEqualTo(commentId);
        assertThat(savedComment.getContent()).isEqualTo(mockComment.getContent());
        assertThat(savedComment.getPostId()).isEqualTo(mockComment.getPostId());
    }

    @DisplayName("댓글 삭제")
    void deleteComment() {

    }
}