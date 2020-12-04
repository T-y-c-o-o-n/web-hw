package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    private Post getPost(String idString) {
        Post post = null;
        try {
            long id = Long.parseLong(idString);
            post = postService.findById(id);
        } catch (NumberFormatException ignored) {
        }
        return post;
    }

    @Guest
    @GetMapping("/post/{idString}")
    public String post(@PathVariable String idString, Model model, HttpSession session) {
        Post post = getPost(idString);
        if (post != null) {
            model.addAttribute("post", post);
            if (getUser(session) != null) {
                model.addAttribute("comment", new Comment());
            }
        }

        return "PostPage";
    }

    @PostMapping("/post/{idString}")
    public String writeComment(@PathVariable String idString, Model model,
                               @Valid @ModelAttribute(name = "comment") Comment comment,
                               BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(getPost(idString));
            return "PostPage";
        }

        User user = getUser(session);
        Post post = getPost(idString);
        if (user != null && post != null) {
            postService.writeComment(comment, user, post);
        }

        return "redirect:/post/{idString}";
    }
}
