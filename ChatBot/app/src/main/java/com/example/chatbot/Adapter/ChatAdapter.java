package com.example.chatbot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatbot.Model.Chat;
import com.example.chatbot.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Chat> listChat;

    public ChatAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Chat> listChat) {
        this.listChat = listChat;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Chat.BOT_TYPE) {
            View view = LayoutInflater.from(this.context).inflate(R.layout.item_bot_chat, parent, false);
            return new BotViewHolder(view);
        }
        View view = LayoutInflater.from(this.context).inflate(R.layout.item_client_chat, parent, false);
        return new ClientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = listChat.get(position);
        if (chat == null)
            return;
        if(chat.getType() == Chat.BOT_TYPE) {
            holder = (BotViewHolder) holder;
            ((BotViewHolder) holder).message.setText(chat.getMessage());
        } else {
            holder = (ClientViewHolder) holder;
            ((ClientViewHolder) holder).message.setText(chat.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return listChat.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listChat.get(position).getType();
    }

    public class BotViewHolder extends RecyclerView.ViewHolder {

        private TextView message;

        public BotViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }

    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {

        private TextView message;

        public ClientViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }

    }

}
