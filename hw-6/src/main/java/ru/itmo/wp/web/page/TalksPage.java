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
        view.put("talks", talkService.findAll().stream()
                .filter((el) -> (el.getSourceUserId() == getUser().getId()
                        || el.getTargetUserId() == getUser().getId())).collect(Collectors.toList()));
        view.put("users", userService.findAll());
    }

    private void action(HttpServletRequest request, Map<String, Object> view) { }

    private void send(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long sourceUserId = getUser().getId();
        String targetLogin = request.getParameter("targetLogin");
        String text = request.getParameter("text");
        talkService.validate(sourceUserId, targetLogin, text);

        Talk talk = new Talk(sourceUserId, userService.findByLogin(targetLogin).getId(), text);
        talkService.addTalk(talk);

        throw new RedirectException("/talks");
    }
}
