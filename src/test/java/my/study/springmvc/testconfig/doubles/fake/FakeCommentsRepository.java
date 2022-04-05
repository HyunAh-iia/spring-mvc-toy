package my.study.springmvc.testconfig.doubles.fake;

import my.study.springmvc.model.comments.Comment;
import my.study.springmvc.model.comments.CommentsRepository;
import my.study.springmvc.testconfig.utils.ReflectionField;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FakeCommentsRepository extends ReflectionField implements CommentsRepository {
    LinkedHashSet<Comment> comments = new LinkedHashSet<>();

    @Override
    public Page<Comment> findAllByPostId(Long postId, Pageable pageable) {
        return new PageImpl<>(
                comments.stream()
                        .filter(comment -> comment.getPostId().equals(postId))
                        .toList()
        );
    }

    @Override
    public List<Comment> findAllByParentIdIn(Collection<Long> parentIds) {
        return comments.stream()
                .filter(comment -> parentIds.contains(comment.getParentId()))
                .toList();
    }

    @Override
    public List<Comment> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Comment> findAll(Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Page<Comment> findAll(Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Comment> findAllById(Iterable<Long> longs) {
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
    public void delete(Comment entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll(Iterable<? extends Comment> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Comment> S save(S entity) {
        autoIncrement(entity);
        comments.add(entity);
        return entity;
    }

    @Override
    public <S extends Comment> List<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Comment> findById(Long aLong) {
        throw new UnsupportedOperationException();
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
    public <S extends Comment> S saveAndFlush(S entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Comment> List<S> saveAllAndFlush(Iterable<S> entities) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteAllInBatch(Iterable<Comment> entities) {
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
    public Comment getOne(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comment getById(Long aLong) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Comment> Optional<S> findOne(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Comment> List<S> findAll(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Comment> List<S> findAll(Example<S> example, Sort sort) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Comment> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Comment> long count(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Comment> boolean exists(Example<S> example) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <S extends Comment, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new UnsupportedOperationException();
    }
}
