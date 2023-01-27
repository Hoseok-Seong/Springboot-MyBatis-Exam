package shop.mtcoding.exam.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import shop.mtcoding.exam.model.User;
import shop.mtcoding.exam.model.UserRepository;
import shop.mtcoding.exam.util.Script;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @PostMapping("/join")
    @ResponseBody
    public String join(String username, String password, String email) {
        try {
            int result = userRepository.insert(username, password, email);
            if (result != 1) {
                return Script.back("회원가입실패");
            }
        } catch (Exception e) {
            return Script.back("회원가입실패");
        }
        return Script.path("/loginForm");
    }

    @GetMapping({ "/", "/loginForm" })
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

        return "redirect:/board";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/loginForm";
    }

    @GetMapping("/updateForm")
    public String updateForm(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/notfound";
        }

        User user = userRepository.findById(principal.getId());
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable int id, String password, String email) {
        // 유효성 검사
        if (password == null || password.isEmpty()) {
            return "redirect:/notfound";
        }

        if (email == null || email.isEmpty()) {
            return "redirect:/notfound";
        }

        // 인증
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/notfound";
        }

        // 권한 체크
        if (principal.getId() != id) {
            return "redirect:/notfound";
        }

        // 업데이트

        int result = userRepository.updateById(id, password, email);

        if (result != 1) {
            return "redirect:/notfound";
        }

        // 세션 동기화
        User user = userRepository.findById(id);
        session.setAttribute("principal", user);

        return "redirect:/logout";
    }

}
