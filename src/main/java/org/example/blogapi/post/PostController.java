package org.example.blogapi.post;

import org.example.blogapi.comment.dto.CommentResponse;
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
            // get all comments for each post
            List<CommentResponse> commentResponses = post.getComments().stream().map(comment -> new CommentResponse(
                    comment.getId(),
                    comment.getText(),
                    comment.getAuthor(),
                    comment.getCreatedAt(),
                    comment.getPost().getId()
            )).collect(Collectors.toList());

            // get all the tag names for each post
            List<String> tagNames = post.getTags().stream().map(tag -> tag.getName())
                    .collect(Collectors.toList());

            return new PostResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    post.getCreatedAt(),
                    tagNames,
                    commentResponses
            );
        }).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);

        // get all comments of this post
        List<CommentResponse> comments = post.getComments().stream().map(comment -> new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getPost().getId()
        )).collect(Collectors.toList());

        // get all tags of this post
        List<String> tags = post.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList());

        PostResponse response = new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                tags,
                comments
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePostRequest request) {
        // pass DTO to the service (which handles the business logic)
        Post createdPost = postService.createPost(request);

        PostResponse response = new PostResponse(
                createdPost.getId(),
                createdPost.getTitle(),
                createdPost.getContent(),
                createdPost.getCreatedAt(),
                createdPost.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList()),
                List.of() // pass an empty list of comments on creation
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id, @RequestBody CreatePostRequest request) {
        // pass DTO to the service (which handles the business logic)
        Post updatedPost = postService.updatePost(id, request);

        // get comments and map them to DTOs
        List<CommentResponse> commentResponses = updatedPost.getComments().stream().map(comment -> new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getPost().getId()
        )).collect(Collectors.toList());

        // convert updatedPost to response DTO
        PostResponse response = new PostResponse(
                updatedPost.getId(),
                updatedPost.getTitle(),
                updatedPost.getContent(),
                updatedPost.getCreatedAt(),
                updatedPost.getTags().stream().map(tag -> tag.getName()).collect(Collectors.toList()),
                commentResponses
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
