package evento.com.evento.model.beans;

import java.io.Serializable;

/**
 * Created by Mohamed on 6/22/2016.
 */
public class UserRepresentation implements Serializable {
    private String id;
    private String name;
    private String imageUrl;

    public UserRepresentation() {
        id="";
        name="";
        imageUrl="";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
