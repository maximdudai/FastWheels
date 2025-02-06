package pt.ipleiria.estg.dei.fastwheels.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import pt.ipleiria.estg.dei.fastwheels.R;
import pt.ipleiria.estg.dei.fastwheels.model.Chat;
import pt.ipleiria.estg.dei.fastwheels.model.ChatMessage;

public class ChatAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Chat> chatList;

    public ChatAdapter(Context context, ArrayList<Chat> chatList) {
        this.context = context;
        this.chatList = chatList;
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

    public View getView(int i, View view, ViewGroup viewGroup) {

        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(view == null) {
            view = inflater.inflate(R.layout.chat_list, null);
        }

        ViewHolderLista viewHolderLista = (ViewHolderLista) view.getTag();
        if(viewHolderLista == null) {
            viewHolderLista = new ViewHolderLista(view);
            view.setTag(viewHolderLista);
        }

        viewHolderLista.update(chatList.get(i));

        return view;
    }

    private class ViewHolderLista {
        private TextView chatTime, chatUsername, chatMessage;

        public ViewHolderLista(View view) {
            chatMessage = view.findViewById(R.id.chatMessage);
            chatUsername = view.findViewById(R.id.chatUsername);
            chatTime = view.findViewById(R.id.chatTime);
        }

        public void update(Chat chat) {
            chatMessage.setText(chat.getChatMessages());
            chatUsername.setText(chat.getChatUsername());
            chatTime.setText(chat.getChatHour());
        }
    }
}
