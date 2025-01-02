/*
package pt.ipleiria.estg.dei.fastwheels.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.R;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;

public class VehicleListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Vehicle> vehicles;

    public VehicleListAdapter(Context context, ArrayList<Vehicle> vehicles) {
        this.context = context;
        this.vehicles = vehicles;
    }

    @Override
    public int getCount() {
        return vehicles.size();
    }

    @Override
    public Object getItem(int i) {
        return vehicles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return vehicles.get(i).getId();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.vehicle_list_item, null);
        }

        ViewHolderVehicle viewHolder = (ViewHolderVehicle) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolderVehicle(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(vehicles.get(position));
        return convertView;
    }

    private class ViewHolderVehicle {
        private ImageView imgVehicle;
        private TextView tvBrand, tvModel, tvYear, tvLocation, tvRating, tvTrips;

        public ViewHolderVehicle(View view) {
            imgVehicle = view.findViewById(R.id.imgVehicle);
            tvBrand = view.findViewById(R.id.tvBrand);
            tvModel = view.findViewById(R.id.tvModel);
            tvYear = view.findViewById(R.id.tvYear);
            tvLocation = view.findViewById(R.id.tvLocation);
            tvRating = view.findViewById(R.id.tvRating);
            tvTrips = view.findViewById(R.id.tvTrips);
        }

        public void update(Vehicle vehicle) {
            imgVehicle.setImageResource(R.drawable.car_test);
            tvBrand.setText(vehicle.getBrand());
            tvModel.setText(vehicle.getCarModel());
            tvYear.setText(String.valueOf(vehicle.getCarYear()));
            tvLocation.setText(vehicle.getLocation());

            tvRating.setText("0.0"); // Replace
            tvTrips.setText("0 trips"); // Replace
        }
    }

}
*/