package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class UserVehicles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vehicles);

        if (savedInstanceState == null) {
            String fragmentTag = getIntent().getStringExtra("TAG_Vehicle");

            if ("VehicleListFragment".equals(fragmentTag)) {
                loadFragment(new VehicleListFragment(), "VehicleListFragment");
            } else {
                loadFragment(new UserVehicleListFragment(), "UserVehicleListFragment");
            }
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

        if (currentFragment != null && currentFragment instanceof UserVehicleListFragment) {
            super.onBackPressed();
            finish();
        } else if (currentFragment != null && currentFragment instanceof VehicleListFragment) {
            super.onBackPressed();
            finish();
        }  else {
            // Volta para o fragmento anterior
            getSupportFragmentManager().popBackStack();
        }
    }
}