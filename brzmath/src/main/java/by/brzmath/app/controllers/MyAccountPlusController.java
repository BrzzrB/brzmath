package by.brzmath.app.controllers;

import by.brzmath.app.models.Post;
import by.brzmath.app.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MyAccountPlusController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/task/{id}")
    public String MyAccountTask(@PathVariable(value = "id") Long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "taskDetails";
    }

    @GetMapping("/task/{id}/edit")
    public String MyAccountTaskEdit(@PathVariable(value = "id") Long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Post> post = postRepository.findById(id);
        ArrayList<Post> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("post", res);
        return "edit";
    }

    @PostMapping("/task/{id}/edit")
    public String TaskUpdate(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String condition, @RequestParam String theme) {
        Post post = postRepository.findById(id).get();
        post.setTitle(title);
        post.setCondition(condition);
        post.setTheme(theme);
        postRepository.save(post);
        return "redirect:/";
    }

    @PostMapping("/{id}/remove")
    public String TaskDelete(@PathVariable(value = "id") Long id) {
        Post post = postRepository.findById(id).get();
        postRepository.delete(post);
        return "redirect:/MyAccount";
    }

    @PostMapping("/admin/{userId}/{taskId}/remove")
    public String TaskDeleteAdmin(@PathVariable(value = "userId") String userId, @PathVariable(value = "taskId") Long taskId) {
        Post post = postRepository.findById(taskId).get();
        postRepository.delete(post);
        return "redirect:/admin/{userId}";
    }
}
