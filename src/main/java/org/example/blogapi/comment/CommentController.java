package org.example.blogapi.comment;

import org.example.blogapi.comment.dto.CommentResponse;
import org.example.blogapi.comment.dto.CreateCommentRequest;
import org.example.blogapi.post.Post;
import org.example.blogapi.post.PostService;
import org.example.blogapi.web.CommentViewHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CommentController {
    private CommentService commentService;
    private PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    @GetMapping("/api/posts/{postId}/comments")
    public List<CommentResponse> getAllComments(@PathVariable Long postId) {
        Post post = postService.getPostById(postId);

        List<Comment> comments = commentService.getCommentsByPostId(post.getId());

        return comments.stream().map(comment -> new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getPost().getId()
        )).collect(Collectors.toList());
    }

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long postId, @RequestBody CreateCommentRequest request) {
        // pass DTO to service to handle business logic
        Comment createdComment = commentService.createComment(postId, request);

        // convert entity to DTO
        CommentResponse response = new CommentResponse(
                createdComment.getId(),
                createdComment.getText(),
                createdComment.getAuthor(),
                createdComment.getCreatedAt(),
                createdComment.getPost().getId()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/api/comments/{id}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long id, @RequestBody CreateCommentRequest request) {
        Comment updatedComment = commentService.updateComment(id, request);

        CommentResponse response = new CommentResponse(
                updatedComment.getId(),
                updatedComment.getText(),
                updatedComment.getAuthor(),
                updatedComment.getCreatedAt(),
                updatedComment.getPost().getId()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
