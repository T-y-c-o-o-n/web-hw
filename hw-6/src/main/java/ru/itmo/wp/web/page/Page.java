package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public abstract class Page {
    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();
    protected final TalkService talkService = new TalkService();
    protected HttpSession session = null;

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        session = request.getSession();
        view.put("userCount", userService.findCount());
    }

    private void action(HttpServletRequest request, Map<String, Object> view) { }

    protected void after(HttpServletRequest request, Map<String, Object> view) { }

    protected void putMessage(Map<String, Object> view) {
        String message = getMessage();
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            session.removeAttribute("message");
        }
    }

    protected void setMessage(String message) {
        session.setAttribute("message", message);
    }

    protected String getMessage() {
        return (String) session.getAttribute("message");
    }

    protected void setUser(User user) {
        session.setAttribute("user", user);
    }

    protected User getUser() {
        return (User) session.getAttribute("user");
    }

}
