package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class UserVehicles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vehicles);

        // Carregar o fragmento inicial (lista de veículos)
        if (savedInstanceState == null) {
            loadFragment(new UserVehicleListFragment());
        }

    };
    // Método para carregar fragmentos
    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentUserVehicleManager, fragment)
                .addToBackStack(null) // Permitir voltar para o fragmento anterior
                .commit();
    }
}