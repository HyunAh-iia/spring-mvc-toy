package my.study.springmvc.services.posts;

import lombok.RequiredArgsConstructor;
import my.study.springmvc.model.posts.Post;
import my.study.springmvc.model.posts.PostRepository;
import my.study.springmvc.model.posts.exception.PostNotFoundException;
import my.study.springmvc.services.medias.Uploader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {
    private static final boolean NOT_DELETED = false;

    private final PostRepository postRepository;
    private final Uploader uploader;

    @Transactional(readOnly = true)
    public Post getPost(Long id) {
        return postRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(PostNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<Post> getPostList(Pageable pageable) {
        return postRepository.findAllByDeleted(NOT_DELETED, pageable);
    }

    @Transactional
    public Post writerPost(Post post, final List<MultipartFile> files) {
        List<String> urls = uploader.upload(files);
        post.withFiles(urls);
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, Post newPost, final List<MultipartFile> files) {
        Post post = getPost(id);
        List<String> urls = uploader.upload(files);
        newPost.withFiles(urls);
        return post.update(newPost);
    }

    @Transactional
    public void deletePost(Long id) {
        getPost(id).delete();
    }
}
