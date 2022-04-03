package my.study.springmvc.testconfig.doubles.fake;

import my.study.springmvc.model.posts.Post;
import my.study.springmvc.model.posts.PostRepository;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class FakePostRepository implements PostRepository {
    private final Set<Post> posts = new LinkedHashSet<>();

    @Override
    public Optional<Post> findByIdAndDeleted(Long id, boolean deleted) {
        return posts.stream()
                .filter(post -> (post.getId() == null || post.getId().equals(id)) && post.isDeleted() == deleted)
                .findFirst();
    }

    @Override
    public Page<Post> findAllByDeleted(boolean deleted, Pageable pageable) {
        return new PageImpl<>(posts.stream()
                .filter(post -> post.isDeleted() == deleted)
                .toList());
    }

    @Override
    public List<Post> findAll() {
        return this.posts.stream().toList();
    }

    @Override
    public List<Post> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Post> findAllById(Iterable<Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Post entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends Post> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        this.posts.clear();
    }

    @Override
    public <S extends Post> S save(S entity) {
        posts.add(entity);
        return entity;
    }

    @Override
    public <S extends Post> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Post> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Post> S saveAndFlush(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Post> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch(Iterable<Post> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Post getOne(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Post getById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Post> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Post> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Post> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Post> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Post> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Post> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Post, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException();
    }
}
