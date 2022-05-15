package craigslist.clone;

import com.google.common.base.Preconditions;

import java.sql.*;

public class DBConnector {

    public static DBConnector get() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = Preconditions.checkNotNull(System.getenv("PS_JDBC"), "ps jdbc was null");
            String user = Preconditions.checkNotNull(System.getenv("PS_USER"), "ps user was null");
            String password = Preconditions.checkNotNull(System.getenv("PS_PASSWORD"), "ps password was null");
            Connection conn = DriverManager.getConnection(connectionUrl, user, password);
            return new DBConnector(conn);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to mySQL server", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load mySQL driver", e);
        }
    }

    private final Connection connection;

    public DBConnector(Connection connection) {
        this.connection = connection;
    }

    public int getNumListings() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM listings");
            ResultSet resultSet = ps.executeQuery();
            // move the cursor to first now.
            resultSet.next();
            // retrieve the count
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get the number of listings.", e);
        }
    }
}
