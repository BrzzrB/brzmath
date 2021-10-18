package by.brzmath.app.controllers;

import by.brzmath.app.services.AdminService;
import by.brzmath.app.services.PostService;
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
    private AdminService adminService;
    private PostService postService;

    @Autowired
    public AdminPageController(UserService userService, AdminService adminService, PostService postService) {
        this.userService = userService;
        this.adminService = adminService;
        this.postService = postService;
    }

    @GetMapping("/admin")
    public String addUser(Model model, Principal principal){

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
        model.addAttribute("users",userService.findAllByOrderByIdAsc());
        model.addAttribute("dateformat",new SimpleDateFormat("yyyy.MM.dd"));
        model.addAttribute("blocked",
                userService.findAllByToken(token).isEmpty() ||
                            userService.findAllByToken(token).get(0).isBlocked());
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
        model.addAttribute("posts", adminService.UserPageAdmin(userId));
        model.addAttribute("userId", userId);
        return "adminUser";
    }
    @GetMapping("/admin/task/{id}/edit")
    public String MyAccountTaskViewEditAdmin(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("post", postService.PostsByIdGet(id));
        return "adminEdit";
    }
    @PostMapping("/admin/task/{id}/edit")
    public String MyAccountTaskEditAdmin(@PathVariable(value = "id") Long id, @RequestParam String title, @RequestParam String condition, @RequestParam String theme, Model model) {
        model.addAttribute("post", adminService.TaskEditAdmin(id, title, condition, theme));
        return "redirect:/admin";
    }
    @GetMapping("/admin/task/{id}/view")
    public String MyAccountTaskViewAdmin(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("post", adminService.TaskViewAdmin(id));
        return "adminView";
    }
    @PostMapping("/admin/{taskId}/remove")
    public String TaskDeleteAdmin( @PathVariable(value = "taskId") Long taskId) {
        adminService.DeleteTaskByIdAdmin(taskId);
        return "redirect:/admin";
    }
    @GetMapping("/admin/addNew/{useridAdmin}")
    public String AddNewTaskAdmin() {
          return "addNewAdmin";
    }
    @PostMapping("/admin/addNew/{userIdAdmin}")
    public String AddNewTaskAdmin(
                             @PathVariable(value = "userIdAdmin") String userIdAdmin,
                             @RequestParam String title,
                             @RequestParam String condition,
                             @RequestParam String theme,
                             @RequestParam (value = "answerOne", required = false) String answerOne,
                             @RequestParam (value = "answerTwo", required = false) String answerTwo,
                             @RequestParam (value = "answerThree", required = false) String answerThree) {
        adminService.addNewTaskAdmin(title, condition, theme, userIdAdmin, answerOne, answerTwo, answerThree);
        return "redirect:/MyAccount";
    }
}


