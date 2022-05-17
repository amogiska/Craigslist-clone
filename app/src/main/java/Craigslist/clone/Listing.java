package craigslist.clone;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Listing {

    private final int listingID;
    private final int userID;
    private final int addressID;
    private final String term;
    private final String date;
    private final int price;
    private final String description;

    @JsonCreator
    public Listing(@JsonProperty("listingID") int listingID, @JsonProperty("userID") int userID, @JsonProperty("addressID") int addressID,
                   @JsonProperty("term") String term, @JsonProperty("date") String date, @JsonProperty("price")int price,
                   @JsonProperty("description") String description) {
        this.listingID = listingID;
        this.userID = userID;
        this.addressID = addressID;
        this.term = term;
        this.date = date;
        this.price = price;
        this.description = description;
    }

    public int getAddressID() {
        return addressID;
    }

    public int getListingID() {
        return listingID;
    }

    public int getPrice() {
        return price;
    }

    public int getUserID() {
        return userID;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getTerm() {
        return term;
    }
}
