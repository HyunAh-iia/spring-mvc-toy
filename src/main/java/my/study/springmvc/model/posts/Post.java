package my.study.springmvc.model.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import my.study.springmvc.core.model.AuditEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor
@Entity
public class Post extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ElementCollection
    @CollectionTable(name = "post_files", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "url")
    private List<String> fileUrls = new ArrayList<>();

    private boolean deleted;

    @Builder
    public Post(final String title, final String content, final List<String> fileUrls) {
        validateTitle(title);
        this.title = title;

        validateContent(content);
        this.content = content;

        this.fileUrls = fileUrls;
        this.deleted = false;
    }

    public Post update(final String title, final String content, final List<String> fileUrls) {
        validateTitle(title);
        this.title = title;

        validateContent(content);
        this.content = content;

        this.fileUrls = fileUrls;

        return this;
    }

    public Post update(final Post newPost) {
        return update(newPost.getTitle(), newPost.getContent(), newPost.getFileUrls());
    }

    public void delete() {
        this.deleted = true;
    }

    public Post addFiles(final List<String> fileUrls) {
        if (this.fileUrls == null) {
            this.fileUrls = fileUrls;
            return this;
        }

        this.fileUrls.addAll(fileUrls);
        return this;
    }

    private void validateTitle(final String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title can not be null or blank");
        }
    }

    private void validateContent(final String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content can not be null or blank");
        }
    }
}
