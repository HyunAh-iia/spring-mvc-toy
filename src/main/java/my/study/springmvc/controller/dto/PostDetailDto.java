package my.study.springmvc.controller.dto;

import my.study.springmvc.model.posts.Post;

public record PostDetailDto(Long id, String title, String content ) {
    public static PostDetailDto of(Post post) {
        return new PostDetailDto(post.getId(), post.getTitle(), post.getContent());
    }
}