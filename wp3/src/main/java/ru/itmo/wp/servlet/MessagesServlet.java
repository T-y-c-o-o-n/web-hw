package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessagesServlet extends HttpServlet {
    private static final String authReqWithUserUriPrefix = "/messages/auth?user=";
    private static final String addReqWithTextUriPrefix = "/messages/add?text=";
    private static List<Message> messages;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        Object data = "";

        if (uri.startsWith("/messages/auth")) {
            System.out.println("here 1");
            String user = request.getParameter("user");
            if (user == null) {
                user = "";
            }
            request.getSession().setAttribute("user", user);
            data = user;
        } else if (uri.startsWith("/messages/findAll")) {
            System.out.println("here 2");
            data = messages;
        } else if (uri.startsWith("/messages/add")) {
            System.out.println("here 3");
            String user = request.getParameter("user");
            String text = request.getParameter("text");

            if (messages == null) {
                messages = new ArrayList<>();
            }
            messages.add(new Message(user, text));
        }

        response.setContentType("application/json");
        String json = new Gson().toJson(data);
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    private static class Message {
        public final String user, name;

        public Message(String user, String name) {
            this.user = user;
            this.name = name;
        }
    }
}
