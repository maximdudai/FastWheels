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
import androidx.fragment.app.FragmentTransaction;

import pt.ipleiria.estg.dei.fastwheels.constants.Constants;
import pt.ipleiria.estg.dei.fastwheels.model.Chat;
import pt.ipleiria.estg.dei.fastwheels.model.SingletonFastWheels;
import pt.ipleiria.estg.dei.fastwheels.model.User;
import pt.ipleiria.estg.dei.fastwheels.modules.Notification;

public class MainActivity extends AppCompatActivity {

    LinearLayout goPerfil, goMeusVeiculos, goVeiculosDisponiveis, goSuporte, goReservations, goVeiculosFavoritos;
    TextView tvMainLoggedName, tvMainLoggedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SingletonFastWheels.getInstance(getApplicationContext()).getUser() == null) {
            Intent toLogin = new Intent(getApplicationContext(), Login.class);
            startActivity(toLogin);
            return;
        }
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
//        goMeusVeiculos = findViewById(R.id.layoutMainMeusVeiculos);
//        goMeusVeiculos.setOnClickListener(v -> {
//            Intent intent = new Intent(MainActivity.this, UserVehicles.class);
//            intent.putExtra("TAG_Vehicle", "UserVehicleListFragment");
//            startActivity(intent);
//        });

        goMeusVeiculos = findViewById(R.id.layoutMainMeusVeiculos);
        goMeusVeiculos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
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

        // VeiculosFavoritos
        goVeiculosFavoritos = findViewById(R.id.layoutMainFavoritos);
        goVeiculosFavoritos.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserVehicles.class);
            intent.putExtra("TAG_Vehicle", "VehicleListFragment");
            intent.putExtra("SHOW_FAVORITES", true);
            startActivity(intent);
        });


        // TODO Reservas
        goReservations = findViewById(R.id.LayoutMainReservas);
        goReservations.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserVehicles.class);
            intent.putExtra("TAG_Vehicle", "ViewHolderReservedVehicles");
            startActivity(intent);
        });

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

        if(loggedUser != null) {
            tvMainLoggedName.setText(loggedUser.getName());
            tvMainLoggedEmail.setText(loggedUser.getEmail());
        } else {
            Intent toLogin = new Intent(getApplicationContext(), Login.class);
            startActivity(toLogin);
        }
    }


}