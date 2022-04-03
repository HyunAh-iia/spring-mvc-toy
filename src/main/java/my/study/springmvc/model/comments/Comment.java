package my.study.springmvc.model.comments;

import lombok.Getter;
import lombok.NoArgsConstructor;
import my.study.springmvc.core.model.AuditEntity;
import my.study.springmvc.model.posts.Post;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
