package my.study.springmvc.services.posts;

import my.study.springmvc.model.posts.Post;
import my.study.springmvc.model.posts.PostRepository;
import my.study.springmvc.model.posts.exception.PostNotFoundException;
import my.study.springmvc.services.medias.Uploader;
import my.study.springmvc.testconfig.doubles.fake.FakePostRepository;
import my.study.springmvc.testconfig.doubles.stub.StubUploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostsServiceTest {
    private final PostRepository postRepository = new FakePostRepository();
    private final Uploader uploader = new StubUploader();

    private PostsService createPostsService() {
        return new PostsService(this.postRepository, this.uploader);
    }

    @DisplayName("게시글 수정")
    @Test
    void updatePost() {
        // given
        Post savedPost = postRepository.save(Post.builder()
                .title("title-before")
                .content(UUID.randomUUID().toString())
                .build());

        Post post = Post.builder()
                .title("title-after")
                .content(UUID.randomUUID().toString())
                .build();

        // when
        PostsService postsService = this.createPostsService();
        postsService.updatePost(savedPost.getId(), post, null);

        // then
        Post updatedPost = postRepository.findAll().get(0);
        assertThat(updatedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(updatedPost.getContent()).isEqualTo(post.getContent());
        assertThat(updatedPost.getFileUrls()).isEqualTo(post.getFileUrls());
    }

    @Test
    @DisplayName("게시글 삭제")
    void deletePost() {
        // given
        Post savedPost = postRepository.save(Post.builder()
                .title("title-before")
                .content(UUID.randomUUID().toString())
                .build());

        // when
        PostsService postsService = this.createPostsService();
        postsService.deletePost(savedPost.getId());

        // then
        assertThat(postRepository.findAll().size()).isEqualTo(1);

        Post deletedPost = postRepository.findAll().get(0);
        assertThat(deletedPost.isDeleted()).isTrue();
    }

    @DisplayName("게시글 저장")
    @Test
    void writerPost() {
        // given
        Post post = Post.builder()
                .title("title")
                .content(UUID.randomUUID().toString())
                .build();

        // when
        PostsService postsService = this.createPostsService();
        Post savedPost = postsService.writerPost(post, null);

        // then
        assertThat(postRepository.findAll().size()).isEqualTo(1);
        assertThat(savedPost.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(savedPost.getContent()).isEqualTo(savedPost.getContent());
        assertThat(savedPost.getFileUrls()).isEqualTo(savedPost.getFileUrls());
    }

    @DisplayName("정상적으로 게시글을 조회")
    @Test
    void getPost() {
        //given
        Post savedPost = postRepository.save(Post.builder()
                .title("title")
                .content(UUID.randomUUID().toString())
                .build());

        // when
        PostsService postsService = this.createPostsService();
        Post post = postsService.getPost(savedPost.getId());

        // then
        //assertThat(post.getId()).isEqualTo(savedPost.getId()); // !@@ 실제 DB에 저장하지 않아 id는 null이 된다. 테스트를 위해 도메인 내 protected withId 같은 걸 만들어야할까?
        assertThat(post.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(post.getContent()).isEqualTo(savedPost.getContent());
        assertThat(post.getFileUrls()).isEqualTo(savedPost.getFileUrls());
    }

    @DisplayName("존재하지 않는 게시글 조회 시 예외 반환")
    @Test
    void throwExceptionWhenGetPostThatIsNotExists() {
        //given
        Long postId = 0L;

        // when
        PostsService postsService = this.createPostsService();

        // then
        assertThatThrownBy(() -> postsService.getPost(postId))
                .isExactlyInstanceOf(PostNotFoundException.class);
    }

    @DisplayName("정상적으로 게시글을 조회")
    @Test
    void getPostList() {
        // given
        postRepository.save(Post.builder()
                .title("title")
                .content(UUID.randomUUID().toString())
                .build());

        postRepository.save(Post.builder()
                .title("title")
                .content(UUID.randomUUID().toString())
                .build());

        Post deletedPost = Post.builder()
                .title("title")
                .content(UUID.randomUUID().toString())
                .build();
        deletedPost.delete();
        postRepository.save(deletedPost);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        PostsService postsService = this.createPostsService();
        Page<Post> postList = postsService.getPostList(pageable);

        // then
        assertThat(postList.getTotalElements()).isEqualTo(2);
    }
}