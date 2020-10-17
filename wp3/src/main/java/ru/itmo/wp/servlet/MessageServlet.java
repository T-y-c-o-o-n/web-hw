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
    private final Chat chat = new Chat();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("MESSAGES");

        String uri = request.getRequestURI();

        Object data = "";

        if ("/messages/auth".equals(uri)) {
            System.out.println("here 1");
            String user = request.getParameter("user");
            if (user == null) {
                user = "";
            }
            request.getSession().setAttribute("user", user);
            data = user;
        } else if ("/messages/findAll".equals(uri)) {
            System.out.println("here 2");
            data = chat.toString();
        } else if ("/messages/add".equals(uri)) {
            System.out.println("here 3");
            String user = request.getParameter("user");
            String text = request.getParameter("text");

            chat.addMessage(user, text);
        }

        response.setContentType("application/json");
        String json = new Gson().toJson(data);
        response.getWriter().print(json);
        response.getWriter().flush();
    }
    
    private static class Chat {
	private final List<Message> messages = new ArrayList<>();
	
	
	public void addMessage(String user, String name) {
	    messages.add(new Message(user, name));
	}

        @Override
        public String toString() {
            return messages.toString();
        }

        private static class Message {
	    public final String user, name;
	
    	    public Message(String user, String name) {
    	        this.user = user;
    	        this.name = name;
    	    }
    	}   
    }
}
