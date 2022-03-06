package info.schuwan.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public interface ConnectionManager {
    void init() throws SQLException;
    void init(Properties props) throws SQLException;
    Connection getConnection() throws SQLException;
    Connection getConnection(String username, String password, String url) throws SQLException;
}
