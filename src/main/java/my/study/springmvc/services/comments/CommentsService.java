package my.study.springmvc.services.comments;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.controller.comments.dto.CommentRepliesDto;
import my.study.springmvc.model.comments.Comment;
import my.study.springmvc.model.comments.CommentsRepository;
import my.study.springmvc.model.comments.exception.CommentNotFound;
import my.study.springmvc.model.comments.exception.CommentReplyDepth;
import my.study.springmvc.services.posts.PostsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final PostsService postsService;

    @Transactional(readOnly = true)
    public Page<CommentRepliesDto> getCommentsWithReplies(Long postId, Pageable pageable) {
        Page<Comment> comments = getComments(postId, pageable);
        List<Long> commentIds = comments.getContent().stream().map(Comment::getId).collect(Collectors.toList());
        List<Comment> replies = commentsRepository.findAllByParentIdIn(commentIds);
        return comments.map(comment -> CommentRepliesDto.of(comment, replies));
    }

    @Transactional
    public Comment writeComment(Comment comment) {
        Long existsPostId = postsService.getPost(comment.getPostId()).getId();
        Comment parentComment = commentsRepository.getById(comment.getParentId());
        if (parentComment.getParentId() != null) {
            throw new CommentReplyDepth();
        }
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

    private Page<Comment> getComments(Long postId, Pageable pageable) {
        Long existsPostId = postsService.getPost(postId).getId();
        return commentsRepository.findAllByPostId(existsPostId, pageable);
    }
}
