package pt.ipleiria.estg.dei.fastwheels;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class UserVehicles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vehicles);

        if (savedInstanceState == null) {
            String fragmentTag = getIntent().getStringExtra("TAG_Vehicle");
            boolean showFavorites = getIntent().getBooleanExtra("SHOW_FAVORITES", false);

            System.out.println("---> fragment oncreate uservehicles: " + showFavorites);

            if ("VehicleListFragment".equals(fragmentTag)) {
                Bundle bundle = new Bundle();
                bundle.putString("SHOW_FAVORITES", String.valueOf(showFavorites ? 1 : 0)); // Convert to String for Bundle

                loadFragment(new VehicleListFragment(), "VehicleListFragment", bundle);

            } else if ("UserVehicleListFragment".equals(fragmentTag)) {
                // meus veículos
                loadFragment(new UserVehicleListFragment(), "UserVehicleListFragment", null);
            } else if ("ViewHolderReservedVehicles".equals(fragmentTag)) {
                // minhas reservas
                loadFragment(new UserReservedVehicleFragment(), "UserVehicleListFragment", null);
            }
        }
    }


    public void loadFragment(Fragment fragment, String tag, Bundle extras) {
        if (extras != null) {
            fragment.setArguments(extras); // Pass extras to the fragment
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentUserVehicleManager, fragment, tag)
                .addToBackStack(null) // Go back to the previous fragment
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
        }  else if (currentFragment != null && currentFragment instanceof UserReservedVehicleFragment) {
            super.onBackPressed();
            finish();
        }
    }
}