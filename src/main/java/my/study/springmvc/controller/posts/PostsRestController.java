package my.study.springmvc.controller.posts;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.controller.ToySuccessResponse;
import my.study.springmvc.controller.posts.dto.PostDetailDto;
import my.study.springmvc.controller.posts.dto.PostWritingRequest;
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
    public ToySuccessResponse<PostDetailDto> getPost(@PathVariable final Long id) {
        return ToySuccessResponse.success(PostDetailDto.of(postService.getPost(id)));
    }

    @GetMapping
    public ToySuccessResponse<Page<PostDetailDto>> getPostList(final Pageable pageable) {
        return ToySuccessResponse.success(postService.getPostList(pageable).map(PostDetailDto::of));
    }

    @PostMapping
    public ToySuccessResponse<PostDetailDto> writePost(@Valid @RequestBody final PostWritingRequest request) {
        return ToySuccessResponse.success(PostDetailDto.of(postService.writerPost(request.toPost())));
    }

    @PutMapping("{id}")
    public ToySuccessResponse<PostDetailDto> updatePost(@PathVariable final Long id, @Valid @RequestBody final PostWritingRequest request) {
        return ToySuccessResponse.success(PostDetailDto.of(postService.updatePost(id, request.toPost())));
    }

    @DeleteMapping("{id}")
    public ToySuccessResponse<Void> deletePost(@PathVariable final Long id) {
        postService.deletePost(id);
        return ToySuccessResponse.empty();
    }
}
