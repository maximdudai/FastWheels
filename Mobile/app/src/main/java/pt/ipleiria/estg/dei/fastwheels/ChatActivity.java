package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pt.ipleiria.estg.dei.fastwheels.model.Chat;

public class ChatActivity extends AppCompatActivity {

    private ListView chatList;

    private EditText textInput;
    private Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


//        chatList = findViewById(R.id.listViewMessages);
        textInput = findViewById(R.id.editTextMessage);
        sendMessage = findViewById(R.id.buttonSend);


        sendMessage.setOnClickListener(v -> {

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}