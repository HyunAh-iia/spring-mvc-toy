package my.study.springmvc.model.comments;

import lombok.Builder;
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

    private boolean deleted;

    @ManyToOne(targetEntity = Post.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", insertable = false, updatable = false)
    private Post post;

    @Column(name = "post_id")
    private Long postId;

    @Builder
    public Comment(final String content, final Long postId) {
        this.content = content;
        this.postId = postId;
        this.deleted = false;
    }

    public void delete() {
        this.deleted = true;
    }

    public String getContent() {
        if (deleted) {
            return "deleted";
        }

        return content;
    }
}
