package my.study.springmvc.core.model;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;
import java.util.Optional;

@ToString
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditEntity {
    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;

//    public AuditEntity(final ZonedDateTime createdAt, final ZonedDateTime updatedAt) {
//        ZonedDateTime now = ZonedDateTime.now();
//        this.createdAt = Optional.ofNullable(createdAt).orElse(now);
//        this.updatedAt = Optional.ofNullable(updatedAt).orElse(now);
//    }
//
//    public AuditEntity() {
//        this(null, null);
//    }
}
