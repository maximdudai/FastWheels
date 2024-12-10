package pt.ipleiria.estg.dei.fastwheels;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pt.ipleiria.estg.dei.fastwheels.modules.Notification;

public class MainActivity extends AppCompatActivity {

    private Button btnMyVehicles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Criar notificações
        Notification notificacao1 = new Notification(Notification.TITLE_WELCOME, "Obrigado por se registrar!");
        Notification notificacao2 = new Notification(Notification.TITLE_SYSTEM_UPDATE, "Confira as novidades em nosso app.");

        // Exibir notificações
        System.out.println(notificacao1);
        System.out.println(notificacao2);

        // Marcar uma notificação como lida
        notificacao1.markAsRead();
        System.out.println("Após marcar como lida:");
        System.out.println(notificacao1);

        Toast.makeText(this, ""+ notificacao1, Toast.LENGTH_SHORT).show();

        loadFragment(new VehicleListFragment());
    }

    private void loadFragment(Fragment fragment) {
        // Gerenciar a transação do fragmento com suporte a addToBackStack
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}