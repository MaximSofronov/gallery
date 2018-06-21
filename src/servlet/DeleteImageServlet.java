package servlet;

import dao.ImageDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlet.DeleteImageServlet", urlPatterns = "/deleteImage")
public class DeleteImageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int imageId = Integer.parseInt(request.getParameter("image_id"));
        int albumId = Integer.parseInt(request.getParameter("album_id"));
        ImageDAO.deleteImage(imageId);
        response.sendRedirect("/album?id=" + albumId);
    }
}
