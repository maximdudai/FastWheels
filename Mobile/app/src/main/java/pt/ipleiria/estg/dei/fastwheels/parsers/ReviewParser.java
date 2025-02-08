package pt.ipleiria.estg.dei.fastwheels.parsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.model.Review;

public class ReviewParser {

    public static ArrayList<Review> parseReviewsData(JSONArray response) {
        ArrayList<Review> reviewsList = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject fetchData = response.getJSONObject(i);

                int id = fetchData.getInt("id");
                int carId = fetchData.getInt("carId");
                String comment = fetchData.getString("comment");
                Timestamp createdAt = Timestamp.valueOf(fetchData.getString("createdAt"));

                Review newReview = new Review(id, carId, comment, createdAt);
                reviewsList.add(newReview);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return reviewsList;
    }

}
