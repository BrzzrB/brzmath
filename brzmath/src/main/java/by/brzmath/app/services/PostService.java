package by.brzmath.app.services;

import by.brzmath.app.models.Post;
import by.brzmath.app.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository)
    {
        this.postRepository = postRepository;
    }

    public ArrayList<Post> PostsByIdGet(Long id)
    {
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        return res;

    }
    public void deletePostById(Long id)
    {

        postRepository.deleteById(id);
    }
    public void editPostById(Long id, String title, String condition, String theme)
    {
        Post post = postRepository.findById(id).get();
        post.setTitle(title);
        post.setCondition(condition);
        post.setTheme(theme);
        postRepository.save(post);
    }
    public void addNewTask(String title, String condition, String theme, String userId)
    {
        Post post = new Post(title, condition, theme, userId);
        postRepository.save(post);
    }
    public Object findAllByUserId(String name)
    {
        Iterable<Post> posts = postRepository.findAllByUserId(name);
        return posts;
    }
}
