package tek.developer.login_generator.servlet;

import tek.developer.login_generator.service.Generator;

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
public class FileHandler extends HttpServlet {
    private final Generator generator = Generator.getGenerator();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        Part filePart = req.getPart("file");
        req.setAttribute("results", generator.generateLoginsFromFile(filePart));

        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }
}
