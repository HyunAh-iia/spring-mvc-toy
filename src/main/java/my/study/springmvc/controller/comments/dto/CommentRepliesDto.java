package my.study.springmvc.controller.comments.dto;

import my.study.springmvc.model.comments.Comment;

import java.util.List;
import java.util.stream.Collectors;

public record CommentRepliesDto(Long id, String content, boolean deleted, List<CommentDto> replies) {
    public static CommentRepliesDto of(final Comment comment, final List<Comment> replies) {
        return new CommentRepliesDto(
                comment.getId(),
                comment.getContent(),
                comment.isDeleted(),
                replies.stream()
                        .filter(reply -> reply.getParentId().equals(comment.getId()))
                        .map(CommentDto::of).collect(Collectors.toList())
        );
    }
}
