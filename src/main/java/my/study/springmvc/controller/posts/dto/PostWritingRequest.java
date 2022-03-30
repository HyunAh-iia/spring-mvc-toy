package my.study.springmvc.controller.posts.dto;

import my.study.springmvc.model.posts.Post;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record PostWritingRequest(@NotBlank String title,
                                 @NotBlank String content,
                                 List<String> fileUrls) {
    public Post toPost() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .fileUrls(this.fileUrls)
                .build();
    }
}
