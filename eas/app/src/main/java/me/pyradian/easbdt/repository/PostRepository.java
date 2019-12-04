package me.pyradian.easbdt.repository;

import me.pyradian.easbdt.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, String> {
}
