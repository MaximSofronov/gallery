package dao;

import model.Album;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AlbumDAO {

    private static final String GET_ALBUMS_QUERY = "SELECT id, name from albums";
    private static final String DELETE_ALBUM_QUERY = "DELETE FROM albums where id = ?";
    private static final String ADD_ALBUM_QUERY = "INSERT into albums (name) VALUES (?)";

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

    static public List<Album> getAlbums() {
        List<Album> albums = null;
        try (Connection albumsConnection = getConnection();
             PreparedStatement albumsPreparedStatement = albumsConnection.prepareStatement(GET_ALBUMS_QUERY);
             ResultSet albumsResultSet = albumsPreparedStatement.executeQuery();
        ) {

            albums = new ArrayList<>();

            while (albumsResultSet.next()) {
                int id = albumsResultSet.getInt(1);
                String text = albumsResultSet.getString(2);
                albums.add(new Album(id, text));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        albums.sort(Comparator.comparingInt(Album::getId));
        return albums;
    }

    public static void deleteAlbum(int id) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_ALBUM_QUERY)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAlbum(String txt) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(ADD_ALBUM_QUERY)) {
            ps.setString(1, txt);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
