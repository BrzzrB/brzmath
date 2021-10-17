package by.brzmath.app.services;

import by.brzmath.app.models.Post;
import by.brzmath.app.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private PostRepository postRepository;

    public AdminService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void DeleteTaskByIdAdmin(Long taskId)
    {
        Post post = postRepository.findById(taskId).get();
        postRepository.delete(post);
    }

    public Iterable<Post> UserPageAdmin(String userId)
    {
        Iterable<Post> posts = postRepository.findAllByUserId(userId);
        return posts;
    }
    public Post TaskViewAdmin(Long taskId)
    {
        Post post = postRepository.findById(taskId).get();
        return post;
    }
    public Object TaskEditAdmin(Long id, String title, String condition, String theme)
    {
        Post post = postRepository.findById(id).get();
        post.setTitle(title);
        post.setCondition(condition);
        post.setTheme(theme);
        postRepository.save(post);
        return null;
    }
    public void addNewTaskAdmin(String title, String condition, String theme, String userId, String answerOne, String answerTwo, String answerThree)
    {
        Post post = new Post(title, condition, theme, userId, answerOne, answerTwo, answerThree);
        postRepository.save(post);
    }
}
