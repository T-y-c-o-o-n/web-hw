package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    private static final String parent = "/home/tycoon/Documents/y2019/web/wp3/src/main/webapp/static";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String rawUri = request.getRequestURI();

        String[] uriArray = rawUri.split("\\+");

        response.setContentType(getContentTypeFromName(uriArray[0]));
        OutputStream outputStream = response.getOutputStream();
        for (String uri : uriArray) {
            File file = new File(parent, uri);
            if (!file.isFile()) {
                file = new File(getServletContext().getRealPath("/static" + uri));
            }
            if (file.isFile()) {
                Files.copy(file.toPath(), outputStream);
                outputStream.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
    }

    private String getContentTypeFromName(String name) {
        name = name.toLowerCase();

        if (name.endsWith(".png")) {
            return "image/png";
        }

        if (name.endsWith(".jpg")) {
            return "image/jpeg";
        }

        if (name.endsWith(".html")) {
            return "text/html";
        }

        if (name.endsWith(".css")) {
            return "text/css";
        }

        if (name.endsWith(".js")) {
            return "application/javascript";
        }

        throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
    }
}
