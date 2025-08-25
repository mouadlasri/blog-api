package org.example.blogapi.post;

import org.example.blogapi.post.dto.CreatePostRequest;
import org.example.blogapi.tag.Tag;
import org.example.blogapi.tag.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    public Post getPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            return optionalPost.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + id);
        }
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

    @Transactional
    public Post updatePost(Long id, CreatePostRequest request) {
        Post postToUpdate = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + id));

        postToUpdate.getTags().clear(); // clear the HashSet of tags

        // update the tags of new post
        for (String tagName : request.getTags()) {
            Tag tag = tagRepository.findByName(tagName)// save new tag, if not exists create new one
                    .orElseGet(() -> new Tag(tagName));

            postToUpdate.getTags().add(tag);
            tag.getPosts().add(postToUpdate); // Maintain the bidirectional relationship
        }

        return postRepository.save(postToUpdate);
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with ID: " + id);
        }
        postRepository.deleteById(id);
    }
}
