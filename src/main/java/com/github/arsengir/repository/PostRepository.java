package com.github.arsengir.repository;

import com.github.arsengir.model.Post;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PostRepository {
    public List<Post> all() {
        return Collections.emptyList();
    }

    public Optional<Post> getById(long id) {
        return Optional.empty();
    }

    public Post save(Post post) {
        return post;
    }

    public void removeById(long id) {

    }
}
