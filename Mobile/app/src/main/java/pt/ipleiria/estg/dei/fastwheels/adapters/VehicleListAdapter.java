package pt.ipleiria.estg.dei.fastwheels.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.R;
import pt.ipleiria.estg.dei.fastwheels.model.Vehicle;
import pt.ipleiria.estg.dei.fastwheels.model.VehiclePhoto;

public class VehicleListAdapter extends BaseAdapter {

    private static Context context;
    private LayoutInflater inflater;
    private ArrayList<Vehicle> vehicles;
    private int layoutResourceId; // Layout a ser utilizado

    public VehicleListAdapter(Context context, ArrayList<Vehicle> vehicles, int layoutResourceId) {
        this.context = context;
        this.vehicles = vehicles;
        this.layoutResourceId = layoutResourceId;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return vehicles.size();
    }

    @Override
    public Object getItem(int position) {
        return vehicles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vehicles.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layoutResourceId, parent, false);
            if (layoutResourceId == R.layout.vehicle_list_item) {
                convertView.setTag(new ViewHolderVehicle(convertView));
            } else if (layoutResourceId == R.layout.item_vehicle) {
                convertView.setTag(new ViewHolderUserVehicle(convertView));
            }
        }

        Vehicle vehicle = vehicles.get(position);

        if (layoutResourceId == R.layout.vehicle_list_item) {
            ViewHolderVehicle viewHolder = (ViewHolderVehicle) convertView.getTag();
            viewHolder.update(vehicle);
        } else if (layoutResourceId == R.layout.item_vehicle) {
            ViewHolderUserVehicle viewHolder = (ViewHolderUserVehicle) convertView.getTag();
            viewHolder.update(vehicle);
        }

        return convertView;
    }

    private class ViewHolderVehicle {
        private ImageView imgVehicle;
        private TextView tvBrand, tvModel, tvYear, tvAddress, tvRating, tvTrips;

        public ViewHolderVehicle(View view) {
            imgVehicle = view.findViewById(R.id.imgVehicle);
            tvBrand = view.findViewById(R.id.tvBrand);
            tvModel = view.findViewById(R.id.tvModel);
            tvYear = view.findViewById(R.id.tvYear);
            tvAddress = view.findViewById(R.id.tvAddress);
            tvRating = view.findViewById(R.id.tvRating);
            tvTrips = view.findViewById(R.id.tvTrips);
        }

        public void update(Vehicle vehicle) {
            imgVehicle.setImageResource(R.drawable.car_test);
            tvBrand.setText(vehicle.getCarBrand());
            tvModel.setText(vehicle.getCarModel());
            tvYear.setText(String.valueOf(vehicle.getCarYear()));
            tvAddress.setText(vehicle.getAddress());

            tvRating.setText("0.0"); // Replace
            tvTrips.setText("0 trips"); // Replace
        }
    }

    private static class ViewHolderUserVehicle {
        private final ImageView ivVeiculo;
        private final TextView tvMarca, tvModelo, tvAno, tvNPortas, tvEstado;

        public ViewHolderUserVehicle(View view) {
            ivVeiculo = view.findViewById(R.id.ivVehicle);
            tvMarca = view.findViewById(R.id.tvListaMarca);
            tvModelo = view.findViewById(R.id.tvListaModelo);
            tvAno = view.findViewById(R.id.tvListaAno);
            tvNPortas = view.findViewById(R.id.tvListaNPortasTitulo);
            tvEstado = view.findViewById(R.id.tvListaEstado);
        }
        public void update(Vehicle vehicle) {

            if (!vehicle.getVehiclePhotos().isEmpty()) {
                VehiclePhoto firstPhoto = vehicle.getVehiclePhotos().get(0);  // Get the first photo object
                String photoUrl = firstPhoto.getPhotoUrl();  // Get the URL string
                Uri photoUri = Uri.parse(photoUrl);

                // Load the image using Glide
                Glide.with(context)
                        .load(photoUri)  // Pass the URI object
                        .placeholder(R.drawable.car_test)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivVeiculo);
            }

            tvMarca.setText(vehicle.getCarBrand());
            tvModelo.setText(vehicle.getCarModel());
            tvAno.setText(String.valueOf(vehicle.getCarYear()));
            tvNPortas.setText(String.valueOf(vehicle.getCarDoors()));
            tvEstado.setText(vehicle.isStatus() ? "Ativo" : "Inativo");
        }
    }
}