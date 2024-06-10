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
import java.util.List;

@WebServlet("/upload")
@MultipartConfig
public class UploadFile extends HttpServlet {
    private final LoginGenerator loginGenerator = LoginGenerator.getLoginGenerator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        Part filePart = req.getPart("file");
        List<LoginGenerator.FullNameAndLogin> results = loginGenerator.generateLoginsFromFile(filePart);

        req.getSession().setAttribute("results", results);
        req.setAttribute("results", results);

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
