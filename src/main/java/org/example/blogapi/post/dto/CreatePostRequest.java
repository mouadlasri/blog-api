package org.example.blogapi.post.dto;

import org.example.blogapi.tag.Tag;

import java.util.List;

public class CreatePostRequest {
    private String title;
    private String content;
    private List<String> tags;

    public CreatePostRequest() {}

    public CreatePostRequest(String title, String content, List<Tag> tags) {
        this.title = title;
        this.content = content;
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
