/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package craigslist.clone;


import io.javalin.Javalin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7070);

        app.get("/", ctx -> ctx.result("Hello World"));

        app.get("/listings/count", ctx -> {
            DBConnector connector = DBConnector.get();
            Map<String, Integer> result = new HashMap<>();
            result.put("total_listing_count", connector.getNumListings());
            ctx.json(result);
        });

        app.get("/listings/all", ctx -> {
            DBConnector connector = DBConnector.get();
            List<Listing> temp = connector.getAllListings();
            ctx.json(temp);
        });

        app.get("/listings/create", ctx -> {
           DBConnector connector = DBConnector.get();
           Listing listing = ctx.bodyAsClass(Listing.class);
           connector.createListing(listing);
        });

        app.get("/user/create", ctx -> {
            DBConnector connector = DBConnector.get();
            User user = ctx.bodyAsClass(User.class);
            connector.createUser(user);
        });

        app.get("/listings/delete/{listingID}", ctx -> {
            DBConnector connector = DBConnector.get();
            try{
                int listingID = Integer.parseInt(ctx.pathParam("listingID"));
                connector.deleteListing(listingID);
            } catch(NumberFormatException e){
                ctx.status(400);
            }


        });

        app.get("/user/delete/{userID}", ctx -> {
           DBConnector connector = DBConnector.get();
           try{
               int userID = Integer.parseInt(ctx.pathParam("userID"));
               connector.deleteUser(userID);
           } catch(NumberFormatException e){
               ctx.status(400);
           }
        });

        app.get("/listings/{userid}", ctx -> {
            DBConnector connector = DBConnector.get();
            try{
                int userid = Integer.parseInt(ctx.pathParam("userid"));
                List<Listing> tmp = connector.getListingForUser(userid);
                ctx.json(tmp);
            } catch(NumberFormatException e){
                ctx.status(400);
            }
        });

        app.get("/url/create", ctx -> {
            DBConnector connector = DBConnector.get();
            Url url = ctx.bodyAsClass(Url.class);
            connector.createTinyurl(url);
        });

        app.get("/url/{urlid}", ctx ->{
            DBConnector connector = DBConnector.get();
            int urlid = Integer.parseInt(ctx.pathParam("userid"));
            String tmp = connector.getBigurl(urlid);
            Map<String, String> result = new HashMap<>();
            result.put("bigurl", tmp);
            ctx.json(result);
        });

    }

}
