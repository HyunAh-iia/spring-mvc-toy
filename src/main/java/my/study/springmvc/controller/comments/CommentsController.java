package my.study.springmvc.controller.comments;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.controller.ToySuccessResponse;
import my.study.springmvc.controller.comments.dto.CommentDto;
import my.study.springmvc.services.comments.CommentsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentsController {
    private final CommentsService commentsService;

    @GetMapping("{postId}/comments")
    public ToySuccessResponse<Page<CommentDto>> getComments(@PathVariable Long postId, Pageable pageable) {
        return ToySuccessResponse.success(
                commentsService.getComments(postId, pageable).map(CommentDto::of)
        );
    }
}
