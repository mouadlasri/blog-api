package org.example.blogapi.post;

import org.example.blogapi.post.dto.PostResponse;
import org.example.blogapi.tag.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts() {
        List<Post> posts = postService.getAll();

        return posts.stream().map(post -> {
            // get all the tag names for each post
            List<String> tagNames = post.getPostTags().stream().map(tag -> tag.getName())
                    .collect(Collectors.toList());

            return new PostResponse(
                    post.getId(),
                    post.getTitle(),
                    post.getContent(),
                    tagNames
            );
        }).collect(Collectors.toList());

    }

}
