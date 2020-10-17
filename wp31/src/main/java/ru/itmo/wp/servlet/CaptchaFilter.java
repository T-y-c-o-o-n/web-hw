package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    Random random = new Random();

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if ("GET".equals(request.getMethod())) {

            Boolean captchaAccepted = (Boolean) request.getSession().getAttribute("captcha-accepted");
            if (captchaAccepted == null) {
                generateCaptcha(request, response);
                return;
            } else if (!captchaAccepted) {
                if (request.getSession().getAttribute("expected-value").equals(
                        Integer.parseInt(request.getParameter("captcha")))) {
                    request.getSession().setAttribute("captcha-accepted", true);
                } else {
                    generateCaptcha(request, response);
                    return;
                }
            }
        }
        chain.doFilter(request, response);

    }

    private void generateCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer expectedValue = random.nextInt(900) + 100;
        request.getSession().setAttribute("expected-value", expectedValue);
        request.getSession().setAttribute("captcha-accepted", false);
        byte[] captchaBytes = Base64.getEncoder().encode(ImageUtils.toPng(expectedValue.toString()));
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
