package dao;

import model.Image;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ImageDAO {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test?useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public List<Image> getImages() {
        List<Image> images = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT id, name, path from images");
             ResultSet resultSet = ps.executeQuery()) {

            images = new ArrayList<>();

            createListOfImagesFromResultSet(images, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    private static void createListOfImagesFromResultSet(List<Image> images, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String path = resultSet.getString(3);
            images.add(new Image(id, name, path));
        }
    }

    static public List<Image> getImagesFromAlbum(int albumId) {
        List<Image> images = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT id, name, path from images WHERE album_id =" + albumId);
             ResultSet resultSet = ps.executeQuery()) {

            images = new ArrayList<>();

            createListOfImagesFromResultSet(images, resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    public static void deleteImage(int id) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM images where id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addImage(String name, String path, int albumId) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT into images (name, path, album_id) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, path);
            ps.setInt(3, albumId);
            System.out.println(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void renameImage(int id, String newName) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE images SET name = ? WHERE id = ?")) {
            ps.setString(1, newName);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
