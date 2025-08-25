package org.example.blogapi.comment.dto;

import java.time.LocalDateTime;

public class CreateCommentRequest {
    private String text;
    private String author;

    public CreateCommentRequest() {}

    public CreateCommentRequest(String text, String author, Long postId) {
        this.text = text;
        this.author = author;
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

}
