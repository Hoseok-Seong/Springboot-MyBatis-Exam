package shop.mtcoding.exam.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shop.mtcoding.exam.model.Board;
import shop.mtcoding.exam.model.BoardRepository;
import shop.mtcoding.exam.model.User;

@Controller
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private HttpSession session;

    @GetMapping("/board")
    public String home(Model model) {
        User principal = (User) session.getAttribute("principal");
        if (principal == null) {
            return "redirect:/notfound";
        }

        List<Board> boardList = boardRepository.findById(principal.getId());
        model.addAttribute("boardList", boardList);
        return "board/list";
    }
}
