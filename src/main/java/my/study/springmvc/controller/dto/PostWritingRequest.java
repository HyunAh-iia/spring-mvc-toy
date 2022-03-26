package my.study.springmvc.controller.dto;

import my.study.springmvc.model.posts.Post;

import javax.validation.constraints.NotBlank;

public record PostWritingRequest(@NotBlank String title,
                                 @NotBlank String content) {
    public Post toPost() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
