package my.study.springmvc.controller.posts;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.controller.ToySuccessResponse;
import my.study.springmvc.controller.posts.dto.PostDetailDto;
import my.study.springmvc.controller.posts.dto.PostWritingRequest;
import my.study.springmvc.services.posts.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ToySuccessResponse<PostDetailDto> writePost(
            @Valid final PostWritingRequest request,
            @RequestPart(value = "files", required = false) final List<MultipartFile> files
    ) {
        return ToySuccessResponse.success(PostDetailDto.of(postService.writerPost(request.toPost(), files)));
    }

    @PutMapping(value = "{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ToySuccessResponse<PostDetailDto> updatePost(
            @PathVariable final Long id,
            @Valid final PostWritingRequest request,
            @RequestPart(value = "files", required = false) final List<MultipartFile> files
    ) {
        return ToySuccessResponse.success(PostDetailDto.of(postService.updatePost(id, request.toPost(), files)));
    }

    @DeleteMapping("{id}")
    public ToySuccessResponse<Void> deletePost(@PathVariable final Long id) {
        postService.deletePost(id);
        return ToySuccessResponse.empty();
    }
}
