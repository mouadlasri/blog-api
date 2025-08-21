package org.example.blogapi.post;

import org.example.blogapi.post.dto.CreatePostRequest;
import org.example.blogapi.tag.Tag;
import org.example.blogapi.tag.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public PostService(PostRepository postRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.tagRepository = tagRepository;
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    public Post createPost(CreatePostRequest request) {
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setCreatedAt(LocalDateTime.now());

        for (String tagName : request.getTags()) {
            Optional<Tag> optionalTag = tagRepository.findByName(tagName);

            // if the tag exists, add it. Otherwise, create and add a new one
            Tag tag = optionalTag.orElseGet(() -> new Tag(tagName));
            post.getTags().add(tag);
            tag.getPosts().add(post); // Important: Maintain the bidirectional relationship
        }

        return postRepository.save(post);
    }
}
