package my.study.springmvc.controller.posts.dto;

import my.study.springmvc.model.posts.Post;

import java.time.ZonedDateTime;

public record PostDetailDto(Long id, String title, String content, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
    public static PostDetailDto of(Post post) {
        return new PostDetailDto(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt(), post.getUpdatedAt());
    }
}