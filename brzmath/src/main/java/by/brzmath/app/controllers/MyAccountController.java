package by.brzmath.app.controllers;

import by.brzmath.app.models.Post;
import by.brzmath.app.models.User;
import by.brzmath.app.repositories.PostRepository;
import by.brzmath.app.repositories.UserRepository;
import by.brzmath.app.services.PostService;
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
    private PostService postService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    public MyAccountController(PostService postService, PostRepository postRepository, UserService userService) {
        this.postService = postService;
        this.postRepository = postRepository;
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
        model.addAttribute("posts", postService.findAllByUserId(auth.getName()));
        model.addAttribute("users", userService.findAllByUserIdPrincipal(auth.getName()));
        model.addAttribute("user_p", auth.getName());

        return "MyAccount";
    }


}
