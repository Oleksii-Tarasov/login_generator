package tek.developer.login_generator.servlet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import tek.developer.login_generator.service.LoginGenerator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/download")
public class DownloadFile extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        List<LoginGenerator.FullNameAndLogin> results = (List<LoginGenerator.FullNameAndLogin>) req.getSession().getAttribute("results");

        if (results == null || results.isEmpty()) {
            req.setAttribute("message", "No results available to download.");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }

        resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        resp.setHeader("Content-Disposition", "attachment; filename=\"logins.xlsx\"");

        try (Workbook workbook = new XSSFWorkbook(); OutputStream out = resp.getOutputStream()) {
            Sheet sheet = workbook.createSheet("Logins");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("#");
            headerRow.createCell(1).setCellValue("Full Name");
            headerRow.createCell(2).setCellValue("Generated Login");

            int rowNum = 1;
            for (LoginGenerator.FullNameAndLogin entry : results) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rowNum - 1);
                row.createCell(1).setCellValue(entry.getFullName());
                row.createCell(2).setCellValue(entry.getLogin());
            }

            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating Excel file.");
        }
    }
}
