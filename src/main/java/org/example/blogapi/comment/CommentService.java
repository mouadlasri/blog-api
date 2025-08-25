package org.example.blogapi.comment;

import org.apache.coyote.Response;
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

    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + postId));

        List<Comment> comments = commentRepository.findByPostId(post.getId());

        return comments;
    }

    @Transactional
    public Comment createComment(Long postId, CreateCommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with ID: " + postId));

        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setAuthor(request.getAuthor());
        comment.setCreatedAt(LocalDateTime.now());

        comment.setPost(post);

        post.getComments().add(comment);

        return commentRepository.save(comment);
     }

     @Transactional
     public Comment updateComment(Long id, CreateCommentRequest request) {
        Comment commentToUpdate = commentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with ID: " + id));

        if (request.getText() != null) {
            commentToUpdate.setText(request.getText());
        }

        if (request.getAuthor() != null) {
            commentToUpdate.setAuthor(request.getAuthor());
        }

        return commentRepository.save(commentToUpdate);
     }

     public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found with ID: " + id);
        }
        commentRepository.deleteById(id);
     }
}
