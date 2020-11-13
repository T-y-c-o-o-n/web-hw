package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TalksPage extends Page {

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            setMessage("To view Chat page you should log in");
            throw new RedirectException("/");
        }
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("talks", talkService.findAll().stream()
                .filter((el) -> (el.getSourceUserId() == getUser().getId()
                        || el.getTargetUserId() == getUser().getId())).collect(Collectors.toList()));
        view.put("users", userService.findAll());
    }

    private void send(HttpServletRequest request, Map<String, Object> view) {
        try {
            Talk talk = new Talk(getUser().getId(),
                    Long.parseLong(request.getParameter("target-user-id")),
                    request.getParameter("talk"));
            talkService.validate(talk);
            talkService.addTalk(talk);

            throw new RedirectException("/talks");
        } catch (NumberFormatException | ValidationException e) {
            if (e instanceof NumberFormatException) {
                setMessage("Choose user");
            } else {
                setMessage(e.getMessage());
            }
            putMessage(view);
            view.put("text", request.getParameter("talk"));
            action(request, view);
        }
    }
}
