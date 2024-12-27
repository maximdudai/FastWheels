package pt.ipleiria.estg.dei.fastwheels;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ExpandableListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ipleiria.estg.dei.fastwheels.adapters.SupportAdapter;

public class Support extends AppCompatActivity {


    ExpandableListView faqListView;
    List<String> categories;
    HashMap<String, List<String>> faqs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        faqListView = findViewById(R.id.faqListView);

        // Load FAQs from JSON
        loadFAQs();

        // Set up the adapter
        SupportAdapter adapter = new SupportAdapter(this, categories, faqs);
        faqListView.setAdapter(adapter);
    }

    private void loadFAQs() {
        categories = new ArrayList<>();
        faqs = new HashMap<>();

        try {
            // Load JSON from raw resource
            InputStream is = getResources().openRawResource(R.raw.faqs);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String jsonString = new String(buffer, "UTF-8");

            // Parse JSON
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject categoryObj = jsonArray.getJSONObject(i);

                // Extract category name
                String category = categoryObj.getString("categoria");
                categories.add(category);

                // Extract questions and answers
                JSONArray questionsArray = categoryObj.getJSONArray("perguntas");
                List<String> questionsAndAnswers = new ArrayList<>();

                for (int j = 0; j < questionsArray.length(); j++) {
                    JSONObject questionObj = questionsArray.getJSONObject(j);
                    String question = questionObj.getString("pergunta");
                    String answer = questionObj.getString("resposta");

                    questionsAndAnswers.add(question + "\n\n" + answer);
                }

                faqs.put(category, questionsAndAnswers);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}