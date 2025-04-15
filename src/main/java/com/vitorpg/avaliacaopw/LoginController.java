package com.vitorpg.avaliacaopw;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {
    private List<User> users = new ArrayList<>();
    @RequestMapping(value = "/cadastro", method = RequestMethod.GET)
    public void getLoginPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var message = request.getParameter("message");
        System.out.println(message);

        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html lang=\"en\">");
        writer.println("<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Login</title>\n" +
                "</head>");
        writer.println("<body>");
        writer.println("<h1>Cadastro</h1>");
        if(message != null && message.equals("emailjaexiste")) {
            writer.println("<p style=\"color:red;\">Email Já Existe</p>");
        }
        writer.println("<form action=\"/cadastro\" method=\"post\">\n" +
                "        <label>Email</label>\n" +
                "        <input type=\"text\" name=\"email\"/>\n" +
                "        <label>Senha</label>\n" +
                "        <input  type=\"password\" name=\"password\"/>\n" +
                "        <button type=\"submit\">Cadastrar</button>\n" +
                "    </form>");
        writer.println("</body>");
        writer.println("</html>");
    }

    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public void cadastro (HttpServletRequest request, HttpServletResponse response) throws IOException {
        var email = request.getParameter("email");
        var password = request.getParameter("password");

        if(users.stream()
                .anyMatch(user -> user.getEmail().equals(email))) {
            response.sendRedirect("/cadastro?message=emailjaexiste");
            return;
        }
        users.add(new User(email, password));
        var session = request.getSession();
        session.setAttribute("email", email);
        response.sendRedirect("/dashboard");
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public void getDashboard (HttpServletRequest request, HttpServletResponse response) throws IOException {
        var session = request.getSession(false);
        if(session == null) {
            response.sendRedirect("/cadastro");
            return;
        }
        response.setContentType("text/html");
        var writer = response.getWriter();
        writer.println("<!DOCTYPE html>");
        writer.println("<html lang=\"en\">");
        writer.println("<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Login</title>\n" +
                "</head>");
        writer.println("<body>");
        writer.println("<h1>Página Principal</h1>");
        writer.println("<p style=\"color:blue;\">Bem vindo!</p>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
