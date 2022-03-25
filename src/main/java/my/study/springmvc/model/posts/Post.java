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
        this.title = title;
        this.content = content;
    }

    public Post update(final String title, final String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public Post update(final Post newPost) {
        this.title = newPost.getTitle();
        this.content = newPost.getContent();
        return this;
    }
}
