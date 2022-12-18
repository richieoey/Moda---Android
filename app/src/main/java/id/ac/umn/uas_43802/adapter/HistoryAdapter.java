package id.ac.umn.uas_43802.adapter;

import android.content.Context;
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

import id.ac.umn.uas_43802.R;
import id.ac.umn.uas_43802.model.CartModel;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder> {
    ArrayList<CartModel> data = new ArrayList<>();
    Context context;

    public HistoryAdapter (ArrayList<CartModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_item_list, parent , false);
        return new HistoryAdapter.MyHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.MyHolder holder, int position) {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
        Glide.with(context).load(data.get(position).getProduct().getPhotoUrl()).apply(options).into(holder.hero);
        holder.name.setText(data.get(position).getProduct().getName());
        holder.toko.setText(data.get(position).getProduct().getToko().get("name").toString());
        holder.price.setText("Rp. "+data.get(position).getProduct().getPrice());
        holder.lokasi.setText(data.get(position).getProduct().getToko().get("province").toString());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends  RecyclerView.ViewHolder{
        ImageView hero;
        TextView name, price, toko, lokasi;
        Context context;
        public MyHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            hero = itemView.findViewById(R.id.image_produk);
            name = itemView.findViewById(R.id.nama_produk);
            price = itemView.findViewById(R.id.harga_produk);
            toko = itemView.findViewById(R.id.nama_toko_history);
            lokasi = itemView.findViewById(R.id.lokasi_toko_history);
        }
    }
}
