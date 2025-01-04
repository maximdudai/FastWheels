package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class UserVehicles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vehicles);

        if (savedInstanceState == null) {
            loadFragment(new UserVehicleListFragment());
        }
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentUserVehicleManager, fragment)
                .addToBackStack(null) // Voltar para o fragmento anterior
                .commit();
    }
}