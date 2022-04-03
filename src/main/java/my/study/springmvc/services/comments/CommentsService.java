package my.study.springmvc.services.comments;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.model.comments.Comment;
import my.study.springmvc.model.comments.CommentsRepository;
import my.study.springmvc.services.posts.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final PostsService postsService;

    public Page<Comment> getComments(Long postId, Pageable pageable) {
        Long existsPostId = postsService.getPost(postId).getId();
        return commentsRepository.findAllByPostId(existsPostId, pageable);
    }
}
