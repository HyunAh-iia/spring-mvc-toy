package my.study.springmvc.model.posts;

import lombok.*;
import my.study.springmvc.core.model.AuditEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Builder
    public Post(final String title, final String content) {
        validateTitle(title);
        this.title = title;

        validateContent(content);
        this.content = content;
    }

    public Post update(final String title, final String content) {
        validateTitle(title);
        this.title = title;

        validateContent(content);
        this.content = content;

        return this;
    }

    public Post update(final Post newPost) {
        return (update(newPost.getTitle(), newPost.getContent()));
    }

    private void validateTitle(final String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title can not be blank");
        }
    }

    private void validateContent(final String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("content can not be blank");
        }
    }
}
