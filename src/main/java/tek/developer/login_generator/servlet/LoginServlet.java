package tek.developer.login_generator.servlet;

import tek.developer.login_generator.service.LoginGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/generate")
public class LoginServlet extends HttpServlet {
    private final LoginGenerator loginGenerator = LoginGenerator.getLoginGenerator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String fullName = req.getParameter("fullName");
        String login = loginGenerator.generateLoginFromFullName(fullName);

        req.setAttribute("login", login);
        req.setAttribute("fullName", fullName);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
