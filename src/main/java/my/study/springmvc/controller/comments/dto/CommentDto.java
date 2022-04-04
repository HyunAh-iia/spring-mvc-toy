package my.study.springmvc.controller.comments.dto;

import my.study.springmvc.model.comments.Comment;

public record CommentDto(Long id, String content, boolean deleted) {
    public static CommentDto of(final Comment comment) {
        return new CommentDto(comment.getId(), comment.getContent(), comment.isDeleted());
    }
}
