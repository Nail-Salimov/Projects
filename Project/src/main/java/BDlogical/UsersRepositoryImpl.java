package BDlogical;

import Model.Comment;
import Model.Post;
import Model.User;
import com.sun.org.apache.xerces.internal.impl.dv.XSSimpleType;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class UsersRepositoryImpl implements UsersRepository {
    private static Connection connection;
    private static UsersRepository repository;

    private static final String INSERT = "INSERT INTO user_date (name, password, mail) VALUES (?, ?, ?)";
    private static final String IS_EXIST = "SELECT 1 FROM user_date WHERE  mail = ?";
    private static final String FIND_USER = "SELECT * FROM user_date WHERE mail = ? and password = ?";
    private static final String FIND_BY_ID = "SELECT * FROM user_date WHERE id = ?";

    private static final String INSERT_POST = "INSERT INTO users_post (users_id, post_image, post_text) VALUES (?, ?, ?)";
    private static final String FIND_ALL_POST = "SELECT * FROM users_post WHERE users_id = ?";

    private static final String INSERT_USERS_IMAGE = "INSERT INTO users_image (users_id, post_image) VALUES (?, ?)";
    private static final String FIND_USERS_IMAGE = "SELECT post_image FROM users_image WHERE users_id = ?";

    private static final String UPDATE_USER_NAME = "UPDATE user_date SET name = ? WHERE id = ?";
    private static final String UPDATE_USER_IMAGE = "UPDATE users_image SET post_image = ? WHERE users_id = ?";

    private static final String SELECT_ALL_USERS_ByNAME = "SELECT * FROM user_date WHERE name = ?";

    private static final String FIND_SUBSCRIBERS = "SELECT idwho FROM subscription WHERE idwhom = ?";
    private static final String FIND_SUBSCRIPTIONS = "SELECT idwhom FROM subscription WHERE idwho = ?";
    private static final String COUNT_SUBSCRIBERS = "SELECT COUNT(*) FROM subscription WHERE idwhom =?";
    private static final String COUNT_SUBSCRIPTION = "SELECT COUNT(*) FROM subscription WHERE idwho =?";

    private static final String IS_SUBSCRIPTION = "SELECT * FROM subscription WHERE idwho = ? and idwhom = ?";
    private static final String SUBSCRIBE = "INSERT INTO subscription (idwho, idwhom) VALUES (?, ?)";
    private static final String UNSUBSCRIBE = "DELETE FROM subscription WHERE idwho = ? and idwhom = ?";

    private static final String FIND_ALL_COMMENTS = "SELECT * FROM users_comment WHERE post_id = ?";
    private static final String INSERT_COMMENT = "INSERT INTO users_comment (post_id, user_id, text) VALUES (?, ?, ?)";

    private static final String FIND_POST = "SELECT * FROM users_post WHERE id = ?";

    private UsersRepositoryImpl() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("/home/nail/Progy/Semester/Project/target/Project-0.0.1/WEB-INF/classes/db.properties"));
            String name = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            String url = properties.getProperty("db.url");
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url, name, password);
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    public static UsersRepository getRepository() {
        if (repository == null) {
            repository = new UsersRepositoryImpl();
        }
        return repository;
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getMail());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            } else {
                statement.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean isExist(String mail) {
        try {
            PreparedStatement statement = connection.prepareStatement(IS_EXIST);
            statement.setString(1, mail);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<User> findUser(String mail, String password) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_USER);
            statement.setString(1, mail);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = userRowMapper.mapRow(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    private RowMapper<User> userRowMapper = (row) -> {
        Long id = row.getLong("id");
        String name = row.getString("name");
        String mail = row.getString("mail");
        String password = row.getString("password");
        return new User(id, name, password, mail);
    };

    private PostMapper<Post> postRowMapper = (row) -> {
        Long usersId = row.getLong("users_id");
        String path = row.getString("post_image");
        String txt = row.getString("post_text");
        return new Post(usersId, path, txt);
    };

    private CommentMapper<Comment> commentMapper = (row) -> {
        Long post_id = row.getLong("post_id");
        Long user_id = row.getLong("user_id");
        String text = row.getString("text");
        User user = findById(user_id).get();
        user.setImage(findUserImage(user.getId()));
        return new Comment(user, text, post_id);
    };

    @Override
    public Optional<User> findById(Long id) {
        User user = null;
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            ;
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = userRowMapper.mapRow(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean save(String name, String password, String mail) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, name);
            statement.setString(2, password);
            statement.setString(3, mail);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            }
            statement.close();
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void savePost(long usersId, String pathImage, String text) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_POST);
            statement.setLong(1, usersId);
            statement.setString(2, pathImage);
            statement.setString(3, text);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            } else {
                statement.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Post> findAllPosts(long usersId) {
        LinkedList<Post> list = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_POST);
            statement.setLong(1, usersId);

            Post post;
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                post = new Post(resultSet.getLong("id"), resultSet.getLong("users_id"), "/image?name=" + resultSet.getString("post_image"), resultSet.getString("post_text"));
                post.setComments(findAllComments(post.getId()));
                list.addFirst(post);
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public Optional<Post> findPost(long postId) {
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_POST);
            statement.setLong(1, postId);
            Post post = null;
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                post = new Post(resultSet.getLong("id"), resultSet.getLong("users_id"), "/image?name=" + resultSet.getString("post_image"), resultSet.getString("post_text"));
                post.setComments(findAllComments(post.getId()));
            }
            return Optional.ofNullable(post);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveImage(long usersId, String pathImage) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USERS_IMAGE);
            statement.setLong(1, usersId);
            statement.setString(2, pathImage);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            } else {
                statement.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void changeName(long userId, String newName) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_NAME);
            statement.setLong(2, userId);
            statement.setString(1, newName);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            } else {
                statement.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void changeImage(long userId, String pathImage) {
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_IMAGE);
            statement.setString(1, pathImage);
            statement.setLong(2, userId);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            } else {
                statement.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String findUserImage(long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_USERS_IMAGE);
            statement.setLong(1, userId);

            String result = "28538.png";
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getString("post_image");
            }
            return "/image?name=" + result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> searchUserByName(String name) {
        List<User> list = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USERS_ByNAME);
            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = userRowMapper.mapRow(resultSet);
                user.setImage(findUserImage(user.getId()));
                list.add(user);
            }

            return list;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> getSubscribers(Long id) {
        LinkedList<User> list = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_SUBSCRIBERS);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.addFirst(findById(resultSet.getLong("idwho")).get());
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<User> getSubscriptions(Long id) {
        LinkedList<User> list = new LinkedList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_SUBSCRIPTIONS);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.addFirst(findById(resultSet.getLong("idwhom")).get());
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int getCountSubscribers(Long id) {
        int count = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(COUNT_SUBSCRIBERS);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public int getCountSubscription(Long id) {
        int count = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(COUNT_SUBSCRIPTION);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public boolean isSubscription(Long idWho, Long idWhom) {
        try {
            PreparedStatement statement = connection.prepareStatement(IS_SUBSCRIPTION);
            statement.setLong(1, idWho);
            statement.setLong(2, idWhom);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void subscribe(Long idWho, Long idWhom) {
        try {
            PreparedStatement statement = connection.prepareStatement(SUBSCRIBE);
            statement.setLong(1, idWho);
            statement.setLong(2, idWhom);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            } else {
                statement.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void unsubscribe(Long idWho, Long idWhom) {
        try {
            PreparedStatement statement = connection.prepareStatement(UNSUBSCRIBE);
            statement.setLong(1, idWho);
            statement.setLong(2, idWhom);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            } else {
                statement.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public List<Comment> findAllComments(Long postId) {
        try {
            LinkedList<Comment> list = new LinkedList<>();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_COMMENTS);
            statement.setLong(1, postId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(commentMapper.mapComment(resultSet));
            }
            return list;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void saveComment(long postId, String text, long userId) {
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_COMMENT);
            statement.setLong(1, postId);
            statement.setLong(2, userId);
            statement.setString(3, text);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException();
            } else {
                statement.close();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
