package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    private final Random random = new Random();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if ("GET".equals(request.getMethod())
                && session.getAttribute("captcha-accepted") == null
                && !request.getRequestURI().equals("/favicon.ico")) {
            if (session.getAttribute("expected-value") != null &&
                    session.getAttribute("expected-value").equals(request.getParameter("captcha"))) {
                session.setAttribute("captcha-accepted", true);
                chain.doFilter(request, response);
            } else {
                generateCaptcha(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int expectedValue;
        synchronized (random) {
            expectedValue = random.nextInt(900) + 100;
        }
        request.getSession().setAttribute("expected-value", Integer.toString(expectedValue));
        byte[] captchaBytes = Base64.getEncoder().encode(ImageUtils.toPng(Integer.toString(expectedValue)));
        String captcha = new String(captchaBytes, StandardCharsets.UTF_8);
        response.setContentType("text/html");
        String html =
                "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Codeforces</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "   <img src=\"data:image/png;base64," + captcha + "\"></img>\n" +
                        "   <form method=\"GET\">\n" +
                        "       <input type=\"text\" name=\"captcha\" value=\"\">\n" +
                        "       <input type=\"submit\" value=\"Enter\">\n" +
                        "   </form>\n" +
                        "<div>Enter captcha</div>\n" +
                        "</body>\n" +
                        "</html>\n";
        response.getWriter().print(html);
        response.getWriter().flush();

    }
}
