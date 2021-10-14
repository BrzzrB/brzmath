package by.brzmath.app.controllers;

import by.brzmath.app.models.Post;
import by.brzmath.app.models.User;
import by.brzmath.app.repositories.PostRepository;
import by.brzmath.app.repositories.UserRepository;
import by.brzmath.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.SimpleDateFormat;

@Controller
public class MyAccountController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    public MyAccountController(UserService userService) {
        this.userService = userService;
    }
    public MyAccountController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/MyAccount")
    public String accountMain(Model model, Principal principal) {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
        if (userService.isContainsByToken(token)){
            userService.setLastDateByUsername(token);
        }else{
            userService.addUserByToken(token);
        }
        model.addAttribute("users",userService.findAllByOrderByIdAsc());
        model.addAttribute("dateformat",new SimpleDateFormat("yyyy.MM.dd"));
        model.addAttribute("blocked",
                userService.findAllByToken(token).isEmpty() ||
                        userService.findAllByToken(token).get(0).isBlocked()
        );
        model.addAttribute("idUser",userService.findAllByToken(token).get(0).getId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Iterable<Post> posts = postRepository.findAllByUserId(auth.getName());
        Iterable<User> users = userRepository.findAllByUserIdPrincipal(auth.getName());
        model.addAttribute("posts", posts);
        model.addAttribute("users", users);
        model.addAttribute("user_p", auth.getName());

        return "MyAccount";
    }

    @PostMapping("/addNew")
    public String AddNewTask(Principal principal, @RequestParam String title, @RequestParam String condition, @RequestParam String theme) {
        String userId = principal.getName();
        Post post = new Post(title, condition, theme, userId);
        postRepository.save(post);
        return "redirect:/MyAccount";
    }
}
