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
            loadFragment(new UserVehicleListFragment(), "UserVehicleListFragment");
        }
    }

    public void loadFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentUserVehicleManager, fragment, tag)
                .addToBackStack(null) // Voltar para o fragmento anterior
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentUserVehicleManager);

        if (currentFragment instanceof UserVehicleListFragment) {
            // Encerra a atividade se o fragmento atual for UserVehicleListFragment
            super.onBackPressed();
            finish();
        }  else {
            // Volta para o fragmento anterior
            getSupportFragmentManager().popBackStack();
        }
    }
}