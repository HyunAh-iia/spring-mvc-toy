package my.study.springmvc.controller;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.controller.dto.PostWritingRequest;
import my.study.springmvc.controller.dto.PostDetailDto;
import my.study.springmvc.services.posts.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("posts")
@RestController
public class PostsRestController {
    private final PostsService postService;

    @GetMapping("{id}")
    public PostDetailDto getPost(@PathVariable final Long id) {
        return PostDetailDto.of(postService.getPost(id));
    }

    @GetMapping
    public Page<PostDetailDto> getPostList(final Pageable pageable) {
        return postService.getPostList(pageable)
                .map(PostDetailDto::of);
    }

    @PostMapping
    public PostDetailDto writePost(@Valid @RequestBody final PostWritingRequest request) {
        return PostDetailDto.of(postService.writerPost(request.toPost()));
    }

    @PutMapping("{id}")
    public PostDetailDto updatePost(@PathVariable final Long id, @Valid @RequestBody final PostWritingRequest request) {
        return PostDetailDto.of(postService.updatePost(id, request.toPost()));
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable final Long id) {
        postService.deletePost(id);
    }
}
