package maskips.id.realmapps.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author nurhidayat
 * @since 5/3/17.
 */

public class Book extends RealmObject{

    @PrimaryKey
    private int id;
    private String title;
    private String description;
    private String author;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
