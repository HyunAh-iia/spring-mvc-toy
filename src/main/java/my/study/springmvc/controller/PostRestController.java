package my.study.springmvc.controller;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.controller.dto.PostWritingRequest;
import my.study.springmvc.controller.dto.PostDetailDto;
import my.study.springmvc.services.posts.PostService;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("post")
@RestController
public class PostRestController {
    private final PostService postService;

    @GetMapping("{id}")
    public PostDetailDto getPost(@PathVariable final Long id) {
        return PostDetailDto.of(postService.getPost(id));
    }

    @PostMapping
    public PostDetailDto writerPost(@RequestBody final PostWritingRequest request) {
        return PostDetailDto.of(postService.writerPost(request.toPost()));
    }
}
