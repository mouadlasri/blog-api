package org.example.blogapi.web;

import org.example.blogapi.comment.Comment;
import org.example.blogapi.comment.CommentService;
import org.example.blogapi.comment.dto.CreateCommentRequest;
import org.example.blogapi.post.Post;
import org.example.blogapi.post.PostService;
import org.example.blogapi.post.dto.CreatePostRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/web/posts")
public class WebPostController {
    private final PostService postService;
    private final CommentService commentService;

    public WebPostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService =  commentService;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getAllPostsHtml() {
        return postService.getAll().stream()
                .map(PostViewHelper::getPostHtml)
                .collect(Collectors.joining());
    }

    @GetMapping(value = "/{id}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        return PostViewHelper.getPostHtml(post);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> createPostHtml(@RequestBody CreatePostRequest request) {
        Post createdPost = postService.createPost(request);
        String postHtml = PostViewHelper.getPostHtml(createdPost);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);

        return new ResponseEntity<>(postHtml, headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/comments", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String getAllCommentsHtml(@PathVariable Long id) {
        List<Comment> comments = commentService.getCommentsByPostId(id);
        return comments.stream()
                .map(comment -> CommentViewHelper.getCommentHtml(comment))
                .collect(Collectors.joining());
    }

    @PostMapping(value = "/{id}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String createCommentHtml(@PathVariable Long id, @RequestBody CreateCommentRequest request) {
        Comment createdComment = commentService.createComment(id, request);
        return CommentViewHelper.getCommentHtml(createdComment);
    }
}