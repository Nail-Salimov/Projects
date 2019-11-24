package BDlogical;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CommentMapper<T> {
    T mapComment(ResultSet var1) throws SQLException;
}
