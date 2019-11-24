package BDlogical;

import Model.Comment;
import Model.Post;
import Model.User;

import java.util.List;
import java.util.Optional;

public interface UsersRepository {
    void save(User user);

    boolean isExist(String mail);

    Optional<User> findUser(String mail, String password);

    Optional<User> findById(Long id);

    void savePost(long usersId, String pathImage, String text);

    List<Post> findAllPosts(long usersId);
    Optional<Post> findPost(long postId);

    void saveImage(long usersId, String pathImage);

    void changeName(long userId, String newName);

    void changeImage(long userId, String pathImage);

    String findUserImage(long userId);

    List<User> searchUserByName(String name);

    List<User> getSubscribers(Long id);

    List<User> getSubscriptions(Long id);

    int getCountSubscribers(Long id);

    int getCountSubscription(Long id);

    boolean isSubscription(Long idWho, Long idWhom);

    void subscribe(Long idWho, Long idWhom);
    void unsubscribe(Long idWho, Long idWhom);

    List<Comment> findAllComments(Long postId);
    void saveComment(long postId, String text, long userId);
}
