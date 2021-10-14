package by.brzmath.app.repositories;

import by.brzmath.app.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    Iterable<Post> findAllByUserId(String userId);
}
