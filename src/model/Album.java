package model;

import java.util.ArrayList;
import java.util.List;

public class Album {
    private int id;
    private String text;
    private List<Integer> imagesIds = new ArrayList<>();

    public List<Integer> getImagesIds() {
        return imagesIds;
    }

    public void setImagesIds(List<Integer> imagesIds) {
        this.imagesIds = imagesIds;
    }

    public Album() {
    }

    public Album(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public Album(int id, String text, List<Integer> imagesIds) {
        this.id = id;
        this.text = text;
        this.imagesIds = imagesIds;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "model.Album{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", imagesIds=" + imagesIds +
                '}';
    }
}
