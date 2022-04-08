package my.study.springmvc.model.comments;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import my.study.springmvc.core.model.AuditEntity;

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

    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Comment parent;

    @Column(name = "parent_id")
    private Long parentId;

    @Builder
    public Comment(final String content, final Long postId, final Long parentId) {
        this.content = content;
        this.postId = postId;
        this.parentId = parentId;
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
