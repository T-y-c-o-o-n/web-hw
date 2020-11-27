package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.NoticeService;

import javax.validation.Valid;

@Controller
public class NoticePage extends Page {


    @GetMapping("notice")
    public String write(Model model) {
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NoticePage";
    }

    @PostMapping("notice")
    public String write(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeForm,
                        BindingResult bindingResult,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }
        Notice notice = new Notice();
        notice.setContent(noticeForm.getContent());
        noticeService.publish(notice);
        model.addAttribute("noticeForm", noticeForm);
        return "redirect:";
    }

}
