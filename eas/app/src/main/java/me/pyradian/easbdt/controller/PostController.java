package me.pyradian.easbdt.controller;

import me.pyradian.easbdt.model.Post;
import me.pyradian.easbdt.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostRepository postRepository;

    @GetMapping("/all")
    public Iterable<Post> allPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable("postId") String postId) {
        return postRepository.findById(postId).get();
    }

    @DeleteMapping("/delete/{postId}")
    public Post deletePost(@PathVariable("postId") String postId) {
        Post post = postRepository.findById(postId).get();

        postRepository.deleteById(postId);

        return post;
    }

    @PostMapping("/upvote/{postId}")
    public Post upvotePost(@PathVariable("postId") String postId) {
        Post post = postRepository.findById(postId).get();

        post.setUpvotes(post.getUpvotes()+1);

        postRepository.save(post);

        return post;
    }

    @PostMapping("/")
    public Post createPost(@RequestBody Post post) {
        System.out.println(post);

        postRepository.save(post);

        return post;
    }
}
