package by.brzmath.app.controllers;

import by.brzmath.app.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @Autowired
    private PostService postService;

    public MainPageController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping("/")
    public String accountIndex(Model model) {
        model.addAttribute("posts", postService.findAllIndex());

        return "index";
    }

    @GetMapping("/addNew")
    public String AddNew() {
        return "addNew";
    }
}
