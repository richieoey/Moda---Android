package id.ac.umn.uas_43802.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.umn.uas_43802.R;
import id.ac.umn.uas_43802.model.ChatModel;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyHolder> {
    ArrayList<ChatModel> data = new ArrayList<>();
    Context context;
    public ChatAdapter (ArrayList<ChatModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_item_list, parent , false);
        return new ChatAdapter.MyHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.hero.setImageResource(data.get(position).getFeatured_image());
        holder.name.setText(data.get(position).getName_store());
        holder.latest_chat.setText(data.get(position).getName_latest_chat());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends  RecyclerView.ViewHolder{
        ImageView hero;
        TextView name, latest_chat;
        Context context;
        public MyHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            hero = itemView.findViewById(R.id.image_store);
            name = itemView.findViewById(R.id.nama_toko);
            latest_chat = itemView.findViewById(R.id.chat_teks);
        }
    }
}
