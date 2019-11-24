package Model;

public class Comment {
   private User user;
   private String text;
   private Long postId;

    public Comment(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public Comment(User user, String text, Long postId) {
        this.user = user;
        this.text = text;
        this.postId = postId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
