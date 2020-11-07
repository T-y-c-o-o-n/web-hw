package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        view.put("talks", talkService.findAll());
        view.put("users", userService.findAll());
    }

    private void send(HttpServletRequest request, Map<String, Object> view) {
        try {
            talkService.addTalk(new Talk(getUser().getId(),
                    Long.parseLong(request.getParameter("target-user-id")),
                    request.getParameter("talk")));
            throw new RedirectException("/talks");
        } catch (NumberFormatException ignored) {
            setMessage("Choose user");
            putMessage(view);
            view.put("text", request.getParameter("talk"));
            action(request, view);
        }
    }
}
