package Model;

import java.util.List;

public class Post {
    private Long id;
    private Long usersId;
    private String imagesPath;
    private String text;
    private List<Comment> comments;

    public Post(Long usersId, String imagesPath, String text) {
        this.usersId = usersId;
        this.imagesPath = imagesPath;
        this.text = text;
    }

    public Post(Long id, Long usersId, String imagesPath, String text) {
        this.id = id;
        this.usersId = usersId;
        this.imagesPath = imagesPath;
        this.text = text;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    public String getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(String imagesPath) {
        this.imagesPath = imagesPath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comment) {
        this.comments = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
