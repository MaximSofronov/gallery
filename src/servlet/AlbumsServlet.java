package servlet;

import dao.AlbumDAO;

import java.io.IOException;

@javax.servlet.annotation.WebServlet(name = "servlet.AlbumsServlet", urlPatterns = "/albums")
public class AlbumsServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setAttribute("albums", AlbumDAO.getAlbums());
        request.getRequestDispatcher("WEB-INF/albums.jsp").forward(request, response);
    }
}
