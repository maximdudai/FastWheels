package pt.ipleiria.estg.dei.fastwheels;

import static pt.ipleiria.estg.dei.fastwheels.utils.Helpers.showMessage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pt.ipleiria.estg.dei.fastwheels.listeners.MosquittoListener;
import pt.ipleiria.estg.dei.fastwheels.modules.Notification;

public class MainActivity extends AppCompatActivity {

    Button btnMyVehicles;

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

        showMessage(this, ""+ notificacao1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        loadFragment(new VehicleListFragment());

//        btnMyVehicles = findViewById(R.id.btnMeusVeiculos);
//        btnMyVehicles.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, UserVehicles.class);
//            startActivity(intent);
//        });

        // region OPCOES MENU

        // MeusVeiculos
        LinearLayout goMeusVeiculos = findViewById(R.id.layoutMainMeusVeiculos);
        goMeusVeiculos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserVehicles.class);
            startActivity(intent);
        });

        // TODO Favoritos




        // VeiculosDisponiveis
        LinearLayout goVeiculosDisponiveis = findViewById(R.id.LayoutMainVeiculosDisp);
        goVeiculosDisponiveis.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VehicleListFragment.class);
            startActivity(intent);
        });



        // endregion
    }

    private void loadFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.layoutPrincipal, fragment)
                .commit();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return super.onCreateOptionsMenu(menu);
    }

    /*
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFragment);
        if (fragment instanceof VehicleListFragment) {

            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
    */
}