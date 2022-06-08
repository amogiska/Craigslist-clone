package craigslist.clone;


import com.google.common.base.Preconditions;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    private static Listing getListingFromResultSet(ResultSet resultSet){

        try {
            int id = resultSet.getInt("id");
            int userid = resultSet.getInt("userid");
            int addressid = resultSet.getInt("addressid");
            String term = resultSet.getString("term");
            String startdate = resultSet.getString("startdate");
            int price = resultSet.getInt("price");
            String description = resultSet.getString("description");

            Listing listing = new Listing(id, userid, addressid, term, startdate, price, description);

            return listing;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get listings", e);
        }
    }

    public List<Listing> getAllListings(){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("SELECT * FROM listings");
            ResultSet resultSet = ps.executeQuery();

            List<Listing> result = new ArrayList<>();
            while(resultSet.next()){
                Listing listing = getListingFromResultSet(resultSet);
                result.add(listing);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to provide all the listings", e);
        }

    }

    public List<Listing> getListingForUser(int userID){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM listings WHERE userid=?");
            ps.setInt(1, userID);
            ResultSet resultSet = ps.executeQuery();
            List<Listing> result = new ArrayList<>();
            while(resultSet.next()){
                Listing listing = getListingFromResultSet(resultSet);
                result.add(listing);
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Unable to get listings for a specific user", e);
        }

    }

    public void createListing(Listing listing){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            java.util.Date listingDate = sdf.parse(listing.getDate());
            PreparedStatement ps = connection.prepareStatement("INSERT INTO listings(userid, " +
                    "addressid, term, startdate, price, description ) VALUES(?, ?, ?, ?, ?, ?)");
            ps.setInt(1, listing.getUserID());
            ps.setInt(2, listing.getAddressID());
            ps.setString(3, listing.getTerm());
            ps.setDate(4, new java.sql.Date(listingDate.getTime()));
            ps.setInt(5, listing.getPrice());
            ps.setString(6, listing.getDescription());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to insert row into the listings table", e);
        } catch (ParseException e) {
            throw new RuntimeException("Unable to parse date", e);
        }
    }

    public void createUser(User user){
        try {
            PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO user(username, firstname, lastname, " +
                            "phonenum, emailaddress) VALUES(?, ?, ?, ?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFirstname());
            ps.setString(3, user.getLastname());
            ps.setString(4, user.getPhonenum());
            ps.setString(5, user.getEmailaddress());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to insert row into the user table", e);
        }
    }

    public void deleteListing(int listingID){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("DELETE FROM listings WHERE id=?");
            ps.setInt(1, listingID);
            ps.execute();
        } catch (SQLException e) {
           throw new RuntimeException("Unable to delete row from listing", e);
        }
    }

    public void deleteUser(int userId){
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM user WHERE id=?");
            ps.setInt(1, userId);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to delete row from user", e);
        }
    }

    public void createTinyurl(Url url){

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO tinyurl(bigurl, tinyurl) VALUES(?, ?)");
            ps.setString(1, url.getBigurl());
            ps.setString(2, url.getTinyurl());
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException("Unable to insert row into url table", e);
        }

    }

    public String getBigurl(int urlid){
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT bigurl FROM listings WHERE id=?");
            ps.setString(1, String.valueOf(urlid));
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getString("bigurl");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
