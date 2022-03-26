package my.study.springmvc.model.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndDeleted(Long id, boolean deleted);
    Page<Post> findAllByDeleted(boolean deleted, Pageable pageable);
}
