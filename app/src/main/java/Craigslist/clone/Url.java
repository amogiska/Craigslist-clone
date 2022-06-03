package craigslist.clone;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Url {

    private final int id;

    private final String bigurl;

    private final String tinyurl;

    @JsonCreator
    public Url(@JsonProperty("id") int id, @JsonProperty("bigurl") String bigurl){
        this.id = id;
        this.bigurl = bigurl;
        this.tinyurl = getSHA(bigurl);
    }

    public int getId() {
        return id;
    }

    public String getBigurl() {
        return bigurl;
    }

    public String getTinyurl() {
        return tinyurl;
    }

    private static String getSHA(String input)
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        byte[] tmp = md.digest(input.getBytes(StandardCharsets.UTF_8));

        return new String(tmp, StandardCharsets.UTF_8);
    }
}
