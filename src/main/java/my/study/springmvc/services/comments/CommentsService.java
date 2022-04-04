package my.study.springmvc.services.comments;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.model.comments.Comment;
import my.study.springmvc.model.comments.CommentsRepository;
import my.study.springmvc.model.comments.exception.CommentNotFound;
import my.study.springmvc.services.posts.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final PostsService postsService;

    @Transactional(readOnly = true)
    public Page<Comment> getComments(Long postId, Pageable pageable) {
        Long existsPostId = postsService.getPost(postId).getId();
        return commentsRepository.findAllByPostId(existsPostId, pageable);
    }

    @Transactional
    public Comment writeComment(Comment comment) {
        Long existsPostId = postsService.getPost(comment.getPostId()).getId();
        return commentsRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Long existsPostId = postsService.getPost(postId).getId();
        Comment comment = commentsRepository.findById(commentId)
                .orElseThrow(CommentNotFound::new);

        if (!comment.getPostId().equals(existsPostId)) {
            throw new CommentNotFound();
        }

        comment.delete();

    }
}
