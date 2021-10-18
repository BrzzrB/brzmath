package by.brzmath.app.controllers;

import by.brzmath.app.services.PostService;
import by.brzmath.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.text.SimpleDateFormat;

@Controller
public class MyAccountController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    public MyAccountController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/MyAccount")
    public String accountMain(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("posts", postService.findAllByUserId(auth.getName()));
        model.addAttribute("users", userService.findAllByUserIdPrincipal(auth.getName()));
        model.addAttribute("user_p", auth.getName());

        return "MyAccount";
    }
}
