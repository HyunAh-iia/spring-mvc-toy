package my.study.springmvc.controller.posts;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.controller.posts.dto.PostDetailDto;
import my.study.springmvc.services.posts.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("posts")
@Controller
public class PostsController {
    private final PostsService postsService;

    @GetMapping
    public String getPosts(Model model) {
        Page<PostDetailDto> posts = postsService.getPostList(PageRequest.of(0, 100)).map(PostDetailDto::of);
        model.addAttribute("posts", posts);
        return "posts";
    }
}
