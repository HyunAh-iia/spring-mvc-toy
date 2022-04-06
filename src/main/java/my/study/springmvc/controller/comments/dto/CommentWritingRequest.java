package my.study.springmvc.controller.comments.dto;

import my.study.springmvc.model.comments.Comment;

import javax.validation.constraints.NotBlank;

public record CommentWritingRequest(@NotBlank String content, Long parentCommentId) {
    public Comment toComment(final Long postId) {
        return toComment(postId, null);
    }

    public Comment toComment(final Long postId, final Long parentCommentId) {
        return Comment.builder()
                .content(content)
                .postId(postId)
                .parentId(parentCommentId)
                .build();
    }
}
