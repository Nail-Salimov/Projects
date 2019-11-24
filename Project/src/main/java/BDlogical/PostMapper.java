package BDlogical;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface PostMapper<T> {
    T mapPost(ResultSet var1) throws SQLException;
}
