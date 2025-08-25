package org.example.blogapi.post.dto;

import org.example.blogapi.comment.dto.CommentResponse;
import org.example.blogapi.tag.Tag;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private List<String> tags;
    private List<CommentResponse> comments;

    public PostResponse() {}

    public PostResponse(Long id, String title, String content, LocalDateTime createdAt, List<String> tags, List<CommentResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.tags = tags;
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public List<CommentResponse> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponse> comments) {
        this.comments = comments;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
