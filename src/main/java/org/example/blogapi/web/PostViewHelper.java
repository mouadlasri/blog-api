package org.example.blogapi.web;

import org.example.blogapi.comment.Comment;
import org.example.blogapi.post.Post;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class PostViewHelper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String getPostHtml(Post post) {
        String tagsHtml = post.getTags().stream()
                .map(tag -> "<span class='tag'>" + tag.getName() + "</span>")
                .collect(Collectors.joining());
        String formattedDateTime = post.getCreatedAt().format(formatter);

        return String.format(
            "<div class='post' id='post-%d'>" +
                    "<h2><a href='/posts/%d' class='post-title'>%s</a></h2>" +
                    "<p class='post-content'>%s</p>" +
                    "<div class='post-meta'>%s</div>" +
                    "<div class='post-tags'>%s</div>" +
                    "</div>",
            post.getId(),
            post.getId(),
            post.getTitle(),
            post.getContent(),
            formattedDateTime,
            tagsHtml
        );
    }

    public static String getCommentHtml(Comment comment) {
        return String.format(
            "<div class='comment'>" +
                    "<div class='comment-header'>" +
                    "<span class='comment-author'>%s</span>" +
                    "<span class='comment-date'>%s</span>" +
                    "</div>" +
                    "<div class='comment-text'>%s</div>" +
                    "</div>",
            comment.getAuthor(),
            comment.getCreatedAt().toString(),
            comment.getText()

        );
    }
}
