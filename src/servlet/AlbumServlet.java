package servlet;

import dao.ImageDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "servlet.AlbumServlet", urlPatterns = "/album")
public class AlbumServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int albumId = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("images", ImageDAO.getImagesFromAlbum(albumId));
        request.getRequestDispatcher("WEB-INF/album.jsp").forward(request, response);
    }
}
