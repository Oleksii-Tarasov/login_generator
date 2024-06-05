package tek.developer.login_generator.servlet;

import tek.developer.login_generator.service.LoginGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/upload")
@MultipartConfig
public class FileServlet extends HttpServlet {
    private final LoginGenerator loginGenerator = LoginGenerator.getLoginGenerator();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        Part filePart = req.getPart("file");
        req.setAttribute("results", loginGenerator.generateLoginsFromFile(filePart));

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
