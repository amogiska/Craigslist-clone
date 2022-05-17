package craigslist.clone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private final int id;
    private final String username;
    private final String firstname;
    private final String lastname;
    private final String phonenum;
    private final String emailaddress;

    @JsonCreator
    public User(@JsonProperty("id") int id, @JsonProperty("username") String username,
                @JsonProperty("firstname")String firstname, @JsonProperty("lastname")String lastname,
                @JsonProperty("phonenum")String phonenum, @JsonProperty("emailaddress") String emailaddress) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenum = phonenum;
        this.emailaddress = emailaddress;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailaddress() {
        return emailaddress;
    }
}
