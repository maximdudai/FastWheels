package pt.ipleiria.estg.dei.fastwheels.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import pt.ipleiria.estg.dei.fastwheels.R;

public class ImageListAdapter extends BaseAdapter {
    private final Context context;
    private final List<Integer> imageIds; // Lista de IDs de recursos de imagens

    public ImageListAdapter(Context context, List<Integer> imageIds) {
        this.context = context;
        this.imageIds = imageIds;
    }

    @Override
    public int getCount() {
        return imageIds.size();
    }

    @Override
    public Object getItem(int position) {
        return imageIds.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.image_list_item, parent, false);
        }

        ImageView imgVehicle = convertView.findViewById(R.id.imgVehicle);
        imgVehicle.setImageResource(imageIds.get(position)); // Define a imagem

        return convertView;
    }
}