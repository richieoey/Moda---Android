package id.ac.umn.uas_43802.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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

import id.ac.umn.uas_43802.ProdukAdapter;
import id.ac.umn.uas_43802.ProdukDetail;
import id.ac.umn.uas_43802.R;
import id.ac.umn.uas_43802.model.ProductModel;
import id.ac.umn.uas_43802.model.SearchProductModel;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> {

    ArrayList<ProductModel> dataStore;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama;
        TextView tvToko;
        TextView tvHarga;
        ImageView imgStore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.nama_produk);
            tvToko = itemView.findViewById(R.id.nama_toko_search);
            tvHarga = itemView.findViewById(R.id.harga_produk);
            imgStore = itemView.findViewById(R.id.image_produk);
        }
    }

    public SearchProductAdapter (ArrayList<ProductModel> data, Context context) {
        this.dataStore = data;
        this.context = context;
    }


    @NonNull
    @Override
    public SearchProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_product, parent, false);
        return new SearchProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchProductAdapter.ViewHolder holder, int position) {
        TextView txtNama = holder.tvNama;
        TextView txtToko = holder.tvToko;
        TextView txtHarga = holder.tvHarga;
        ImageView ivProduk = holder.imgStore;
        ProductModel produk = dataStore.get(position);

        txtNama.setText(dataStore.get(position).getName());
        txtHarga.setText(dataStore.get(position).getPrice());
        txtToko.setText(dataStore.get(position).getToko().get("name").toString());

        RequestOptions options = new RequestOptions();
        options.centerInside();;

        Glide.with(holder.itemView.getContext()).load(dataStore.get(position).getPhotoUrl()).apply(options).into(ivProduk);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), ProdukDetail.class);
            intent.putExtra("uid", produk.getUid());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataStore.size();
    }

}
