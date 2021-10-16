package by.brzmath.app.controllers;

import by.brzmath.app.services.AdminService;
import by.brzmath.app.services.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class MyAccountPlusController {

    private PostService postService;
    private AdminService adminService;

    public MyAccountPlusController(PostService postService,AdminService adminService) {
        this.postService = postService;
        this.adminService = adminService;
    }

    @GetMapping("/task/{id}")
    public String MyAccountTask(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("post", postService.PostsByIdGet(id));
        return "taskDetails";
    }
    @PostMapping("/addNew")
    public String AddNewTask(Principal principal, @RequestParam String title, @RequestParam String condition, @RequestParam String theme) {
        String userId = principal.getName();
        postService.addNewTask(title, condition, theme, userId);
        return "redirect:/MyAccount";
    }

    @GetMapping("/task/{id}/edit")
    public String MyAccountTaskEdit(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("post", postService.PostsByIdGet(id));
        return "edit";
    }


    @PostMapping("/task/{id}/edit")
    public String TaskUpdate(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String condition, @RequestParam String theme) {
        postService.editPostById(id, title, condition, theme);
        return "redirect:/MyAccount";
    }

    @GetMapping("task/{id}/remove")
    public String TaskDelete(@PathVariable(value = "id") Long id) {
        postService.deletePostById(id);
        return "redirect:/MyAccount";
    }


}
