package servlet;

import dao.ImageDAO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

@WebServlet(name = "servlet.UploadServlet", urlPatterns = "/upload")
public class UploadServlet extends HttpServlet {
    private static final int MB = 1024 * 1024;
    private Random random = new Random();

    private static String pathToFolder = "C:\\Users\\maxim\\Desktop\\images_from_gallery\\";

    private static String pathToFolderOnServer = "http://127.0.0.1:8887/";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int albumId = Integer.parseInt(request.getParameter("album_id"));
        String imageName = "image";

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (!isMultipart) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();

        factory.setSizeThreshold(MB);

        File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(tempDir);

        ServletFileUpload upload = new ServletFileUpload(factory);

        upload.setSizeMax(10 * MB);

        try {
            List items = upload.parseRequest(request);

            for (Object item1 : items) {
                FileItem item = (FileItem) item1;

                if (item.isFormField()) {
                    imageName = getImageName(item);
                } else {
                    processUploadedFile(item, albumId, imageName);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        response.sendRedirect("/album?id=" + albumId);
    }

    private void processUploadedFile(FileItem item, int albumId, String imageName) throws Exception {
        File uploadetFile;

        String name;

        do {
            name = Integer.toString(random.nextInt());
            String path = pathToFolder + name;
            uploadetFile = new File(path);
        } while (uploadetFile.exists());

        uploadetFile.createNewFile();

        item.write(uploadetFile);

        ImageDAO.addImage(imageName, pathToFolderOnServer + name, albumId);
    }

    private String getImageName(FileItem item) {
        return item.getString();
    }
}
