package org.example.blogapi.web;

import org.example.blogapi.comment.Comment;

import java.time.format.DateTimeFormatter;

public class CommentViewHelper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String getCommentHtml(Comment comment) {
        String formattedDateTime = comment.getCreatedAt().format(formatter);
        return String.format(
                "<div class='comment' style='border-left: 3px solid #ccc; padding-left: 10px; margin-bottom: 10px;'>" +
                        "   <p><strong>%s:</strong> %s</p>" +
                        "   <small>%s</small>" +
                        "</div>",
                comment.getAuthor(),
                comment.getText(),
                formattedDateTime
        );
    }
}