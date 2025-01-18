package pt.ipleiria.estg.dei.fastwheels;

import static pt.ipleiria.estg.dei.fastwheels.utils.Helpers.showMessage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.modules.Notification;

public class MainActivity extends AppCompatActivity {

    LinearLayout goPerfil, goMeusVeiculos, goVeiculosDisponiveis, goSuporte;
    TextView tvMainLoggedName, tvMainLoggedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region NOTIFICICACOES
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
        //endregion

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //region CARREGAR DADOS UTILIZADOR
        tvMainLoggedName = findViewById(R.id.tvMainLoggedName);
        tvMainLoggedEmail = findViewById(R.id.tvMainLoggedEmail);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE);

        tvMainLoggedName.setText(sharedPreferences.getString(Constants.KEY_USERNAME, null));
        tvMainLoggedEmail.setText(sharedPreferences.getString(Constants.KEY_EMAIL, null));

        //endregion

        // region OPCOES MENU
        // Perfil
        goPerfil = findViewById(R.id.userInfoSection);
        goPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserProfile.class);
            startActivity(intent);
        });

        // MeusVeiculos
        goMeusVeiculos = findViewById(R.id.layoutMainMeusVeiculos);
        goMeusVeiculos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserVehicles.class);
            intent.putExtra("TAG_Vehicle", "UserVehicleListFragment");
            startActivity(intent);
        });

        // TODO Favoritos


        // Suporte
        goSuporte = findViewById(R.id.layoutMainSuporte);
        goSuporte.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Support.class);
            startActivity(intent);
        });

        // VeiculosDisponiveis
        goVeiculosDisponiveis = findViewById(R.id.LayoutMainVeiculosDisp);
        goVeiculosDisponiveis.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserVehicles.class);
            intent.putExtra("TAG_Vehicle", "VehicleListFragment");
            startActivity(intent);
        });


        // TODO Reservas


        // TODO Notificacoes


        // endregion
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        User loggedUser = SingletonFastWheels.getInstance(getApplicationContext()).getUser();

        tvMainLoggedName.setText(loggedUser.getName());
        tvMainLoggedEmail.setText(loggedUser.getEmail());
    }


}