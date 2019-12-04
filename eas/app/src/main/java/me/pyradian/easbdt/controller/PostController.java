package me.pyradian.easbdt.controller;

import me.pyradian.easbdt.model.Post;
import me.pyradian.easbdt.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
