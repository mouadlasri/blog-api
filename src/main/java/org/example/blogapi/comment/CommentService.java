package org.example.blogapi.comment;

import org.example.blogapi.comment.dto.CreateCommentRequest;
import org.example.blogapi.post.Post;
import org.example.blogapi.post.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public Comment getById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);

        if (optionalComment.isPresent()) {
            return optionalComment.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with ID: " + id);
        }
    }

    @Transactional
    public Comment createComment(CreateCommentRequest request) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + request.getPostId()));

        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setAuthor(request.getAuthor());
        comment.setCreatedAt(LocalDateTime.now());

        comment.setPost(post);

        post.getComments().add(comment);

        return commentRepository.save(comment);
     }

}
