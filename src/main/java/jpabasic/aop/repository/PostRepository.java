package jpabasic.aop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jpabasic.aop.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
