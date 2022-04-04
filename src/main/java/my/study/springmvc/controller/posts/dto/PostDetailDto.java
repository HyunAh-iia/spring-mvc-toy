package my.study.springmvc.controller.posts.dto;

import my.study.springmvc.model.posts.Post;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

public record PostDetailDto(Long id, String title, String content, List<String> fileLinks,
                            ZonedDateTime createdAt, ZonedDateTime updatedAt) {
    public static PostDetailDto of(Post post) {
        return new PostDetailDto(post.getId(), post.getTitle(), post.getContent(), post.getFileUrls(), post.getCreatedAt(), post.getUpdatedAt());
    }

    public List<String> getFileLinks() {
        if (this.fileLinks == null) {
            return Collections.emptyList();
        }
        return this.fileLinks;
    }
}