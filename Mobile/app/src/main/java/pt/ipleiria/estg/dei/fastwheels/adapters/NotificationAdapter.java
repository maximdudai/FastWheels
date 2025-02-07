package pt.ipleiria.estg.dei.fastwheels.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.R;
import pt.ipleiria.estg.dei.fastwheels.model.Notification;

public class NotificationAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Notification> notifications;

    public NotificationAdapter(Context context, ArrayList<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null) {
            view = inflater.inflate(R.layout.notification_list_item, null);
        }

        ViewHolderLista viewHolderLista = (ViewHolderLista) view.getTag();
        if(viewHolderLista == null) {
            viewHolderLista = new ViewHolderLista(view);
            view.setTag(viewHolderLista);
        }

        viewHolderLista.update(notifications.get(i));
        return view;
    }

    private class ViewHolderLista {
        private TextView notHour, notContent;
        private RadioButton notRead;

        public ViewHolderLista(View view) {
            notHour = view.findViewById(R.id.tvDate);
            notContent = view.findViewById(R.id.tvContent);
            notRead = view.findViewById(R.id.radioButton);
        }

        public void update(Notification not) {
            notHour.setText(not.getCreatedAt().toString());

            String minimizedContent = this.getMinimizedContent(not.getContent());
            notContent.setText(minimizedContent);

            notRead.setChecked(not.getRead() == 1 ? true : false);

        }
        private String getMinimizedContent(String content) {
            return content.trim().substring(0, 30);
        }
    }
}
