package org.example.blogapi.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/posts/{id}")
    public String postDetails(@PathVariable Long id, Model model) {
        model.addAttribute("postId", id);
        return "post";
    }
}
