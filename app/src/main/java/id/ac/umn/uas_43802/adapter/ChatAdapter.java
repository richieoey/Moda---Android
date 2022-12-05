package id.ac.umn.uas_43802.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import id.ac.umn.uas_43802.ChatActivity;
import id.ac.umn.uas_43802.ProdukDetail;
import id.ac.umn.uas_43802.R;
import id.ac.umn.uas_43802.model.ChatModel;
import id.ac.umn.uas_43802.model.ProductModel;

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
        RequestOptions options = new RequestOptions();
        options.circleCrop();

        Glide.with(holder.context).load(data.get(position).getPhotoUrl()).apply(options).into(holder.hero);
        holder.name.setText(data.get(position).getName());
        holder.latest_chat.setText(data.get(position).getLastMessage());

        holder.itemView.setOnClickListener(view -> {
            ChatModel person = data.get(position);
            Intent intent = new Intent(holder.itemView.getContext(), ChatActivity.class);
            intent.putExtra("person", person);
            holder.itemView.getContext().startActivity(intent);
        });
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
