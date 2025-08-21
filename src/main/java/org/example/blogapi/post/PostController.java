package org.example.blogapi.post;

import org.example.blogapi.post.dto.CreatePostRequest;
import org.example.blogapi.post.dto.PostResponse;
import org.example.blogapi.tag.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postService.getAll();

        return posts.stream().map(post -> {
            // get all the tag names for each post
            List<String> tagNames = post.getTags().stream().map(tag -> tag.getName())
                    .collect(Collectors.toList());

            return new PostResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCreatedAt(),
                    tagNames
            );
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        Optional<Post> optionalPost = postService.getPostById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            PostResponse response = new PostResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCreatedAt(),
                    post.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList())
            );
            return ResponseEntity.ok(response);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + id);
        }
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest request) {
        Post createdPost = postService.createPost(request);

        PostResponse response = new PostResponse(
                createdPost.getId(),
                createdPost.getTitle(),
                createdPost.getContent(),
                createdPost.getCreatedAt(),
                createdPost.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList())
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }
}
