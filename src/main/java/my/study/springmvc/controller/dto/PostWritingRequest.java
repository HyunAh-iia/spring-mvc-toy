package my.study.springmvc.controller.dto;

import my.study.springmvc.model.posts.Post;

public record PostWritingRequest(String title, String content) {
    public Post toPost() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
