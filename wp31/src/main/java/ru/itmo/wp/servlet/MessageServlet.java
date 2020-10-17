package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageServlet extends HttpServlet {
    private final List<Message> chat = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String json = "";

        if ("/message/auth".equals(uri)) {
            String user = request.getParameter("user");
            if (user == null) {
                user = "";
            }
            request.getSession().setAttribute("user", user);
            json = new Gson().toJson(user);
        } else if ("/message/findAll".equals(uri)) {
            json = new Gson().toJson(chat);
        } else if ("/message/add".equals(uri)) {
            String user = (String) request.getSession().getAttribute("user");
            String text = request.getParameter("text");

            chat.add(new Message(user, text));
        }

        response.setContentType("application/json");
        response.getWriter().print(json);
        response.getWriter().flush();
    }


    private static class Message {
        public final String user, text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }
}
