package org.example.blogapi.comment;

import org.example.blogapi.comment.dto.CommentResponse;
import org.example.blogapi.comment.dto.CreateCommentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public List<CommentResponse> getAllComments() {
        List<Comment> comments = commentService.getAll();

        return comments.stream().map(comment -> new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getPost().getId()
        )).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getById(id); // service handles the not found exception

        CommentResponse response = new CommentResponse(
                comment.getId(),
                comment.getText(),
                comment.getAuthor(),
                comment.getCreatedAt(),
                comment.getPost().getId()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long id, @RequestBody CreateCommentRequest request) {
        // pass DTO to service to handle business logic
        Comment createdComment = commentService.createComment(request);

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

}
