package by.brzmath.app.controllers;

import by.brzmath.app.models.Post;
import by.brzmath.app.repositories.PostRepository;
import by.brzmath.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.text.SimpleDateFormat;

@Controller
public class AdminPageController {
    private final UserService userService;

    @Autowired
    public AdminPageController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/admin")
    public String addUser(Model model, Principal principal){

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
        model.addAttribute("users",userService.findAllByOrderByIdAsc());
        model.addAttribute("dateformat",new SimpleDateFormat("yyyy.MM.dd"));
        model.addAttribute("blocked",
                userService.findAllByToken(token).isEmpty() ||
                        userService.findAllByToken(token).get(0).isBlocked()
        );
        model.addAttribute("idUser",userService.findAllByToken(token).get(0).getId());
        model.addAttribute("users",userService.findAllByOrderByIdAsc());
        model.addAttribute("dateformat",new SimpleDateFormat("yyyy.MM.dd"));
        model.addAttribute("blocked",
                userService.findAllByToken(token).isEmpty() ||
                        userService.findAllByToken(token).get(0).isBlocked()
        );
        model.addAttribute("idUser",userService.findAllByToken(token).get(0).getId());
        return "admin";
    }

    @PostMapping("/admin")
    public String blockAndDeleteUsers(@RequestParam String action, @RequestParam int[] ids){
        for(int id:ids){
            if (action.equals("delete")) {
                userService.deleteById(id);
            } else if (action.equals("lock")) {
                userService.lockById(id);
            } else {
                userService.unlockById(id);
            }
        }
        return "admin";
    }

        @GetMapping("/admin/{userId}")
        public String adminUser(@PathVariable(value = "userId") String userId, Model model) {
            Iterable<Post> posts = postRepository.findAllByUserId(userId);
            model.addAttribute("posts", posts);
            model.addAttribute("userId", userId);
            return "adminUser";
        }
}


