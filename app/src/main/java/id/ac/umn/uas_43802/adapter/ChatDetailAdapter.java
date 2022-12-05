package id.ac.umn.uas_43802.adapter;

import android.content.Context;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import id.ac.umn.uas_43802.R;
import id.ac.umn.uas_43802.model.ChatDetailModel;

public class ChatDetailAdapter extends RecyclerView.Adapter<ChatDetailAdapter.MyHolder> {
    ArrayList<ChatDetailModel> data = new ArrayList<>();
    Context context;
    public ChatDetailAdapter (ArrayList<ChatDetailModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatDetailAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_atas_bawah, parent , false);

        return new ChatDetailAdapter.MyHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user.getUid().compareTo(data.get(position).getSenderId()) == 0){
            holder.chatSender.setText(data.get(position).getMessage());
            holder.timeSender.setText(data.get(position).getTime());
            holder.receiver.setVisibility(View.GONE);
        }else{
            holder.chatReceive.setText(data.get(position).getMessage());
            holder.timeReceive.setText(data.get(position).getTime());
            holder.sender.setVisibility(View.GONE);
        }

//        holder.notify();
//        holder.
//        holder.itemView.setOnClickListener(view -> {
//            ProductModel product = data.get(position);
//            Intent intent = new Intent(holder.itemView.getContext(), ProdukDetail.class);
//            intent.putExtra("produk", product);
//            holder.itemView.getContext().startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends  RecyclerView.ViewHolder{
        TextView timeSender, chatSender, timeReceive, chatReceive;
        LinearLayout receiver, sender;
        Context context;
        public MyHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            timeReceive = itemView.findViewById(R.id.textDateTimeReceiver);
            timeSender = itemView.findViewById(R.id.textDateTime);
            chatReceive = itemView.findViewById(R.id.textMessageReceiver);
            chatSender = itemView.findViewById(R.id.textMessage);
            receiver = itemView.findViewById(R.id.receiver);
            sender = itemView.findViewById(R.id.sender);
        }
    }
}
