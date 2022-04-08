package my.study.springmvc.controller.comments;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.controller.ToySuccessResponse;
import my.study.springmvc.controller.comments.dto.CommentDto;
import my.study.springmvc.controller.comments.dto.CommentRepliesDto;
import my.study.springmvc.controller.comments.dto.CommentWritingRequest;
import my.study.springmvc.services.comments.CommentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("posts/{postId}/comments")
@RequiredArgsConstructor
@RestController
public class CommentsController {
    private final CommentsService commentsService;

    @GetMapping
    public ToySuccessResponse<Page<CommentRepliesDto>> getComments(@PathVariable Long postId, Pageable pageable) {
        return ToySuccessResponse.success(
                commentsService.getCommentsWithReplies(postId, pageable)
        );
    }

    @PostMapping
    public CommentDto writerComment(@PathVariable Long postId, @RequestBody CommentWritingRequest request) {
        return CommentDto.of(commentsService.writeComment(request.toComment(postId)));
    }

    @DeleteMapping("{commentId}")
    public ToySuccessResponse<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentsService.deleteComment(postId, commentId);
        return ToySuccessResponse.empty();
    }
}
