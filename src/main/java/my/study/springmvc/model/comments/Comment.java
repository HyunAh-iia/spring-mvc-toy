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
        validateContent(content);
        this.content = content;

        validatePostId(postId);
        this.postId = postId;

        validateParentId(parentId);
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

    private void validateContent(final String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content can not be null or blank");
        }
    }

    private void validatePostId(final Long postId) {
        if (postId == null) {
            throw new IllegalArgumentException("postId can not be null");
        }

        if (postId < 1) {
            throw new IllegalArgumentException("postId must be greater than 0");
        }
    }

    private void validateParentId(final Long parentId) {
        if (parentId == null) {
            return;
        }

        if (parentId < 1) {
            throw new IllegalArgumentException("parentId must be greater than 0");
        }
    }
}
