package my.study.springmvc.controller.comments.dto;

import my.study.springmvc.model.comments.Comment;

import javax.validation.constraints.NotBlank;

public record CommentWritingRequest(@NotBlank String content) {
    public Comment toComment(final Long postId) {
        return Comment.builder()
                .content(content)
                .postId(postId)
                .build();
    }
}
