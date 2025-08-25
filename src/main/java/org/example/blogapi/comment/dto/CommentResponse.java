package org.example.blogapi.comment.dto;

import java.time.LocalDateTime;

public class CommentResponse {
    private Long id;
    private String text;
    private String author;
    private LocalDateTime createdAt;
    private Long postId;

    public CommentResponse() {}

    public CommentResponse(Long id, String text, String author, LocalDateTime createdAt, Long postId) {
        this.id = id;
        this.text = text;
        this.author = author;
        this.createdAt = createdAt;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
