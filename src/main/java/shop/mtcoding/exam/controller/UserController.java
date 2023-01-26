package shop.mtcoding.exam.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.mtcoding.exam.model.User;
import shop.mtcoding.exam.model.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @GetMapping({ "/", "/joinForm" })
    public String joinForm() {
        return "user/joinForm";
    }

    @PostMapping("/join")
    public String join(String username, String password, String email) {
        int result = userRepository.insert(username, password, email);
        if (result == 1) {
            return "redirect:/loginForm";
        } else {
            return "redirect:/joinForm";
        }
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model) {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            return "redirect:/loginForm";
        }

        session.setAttribute("principal", user);

        // model.addAttribute("userId", user.getId());
        // session.setAttribute("userId", user.getId());

        // return "board/list";
        return "redirect:/board";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/loginForm";
    }

    @GetMapping("/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @PostMapping("/update")
    public String update(String password, String email) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/notfound";
        }

        int result = userRepository.updateById(principal.getUsername(), password, email);

        if (result == -1) {
            return "redirect:/notfound";
        }

        return "redirect:/logout";
    }

}
