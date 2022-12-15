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

import id.ac.umn.uas_43802.R;
import id.ac.umn.uas_43802.StoreDetail;
import id.ac.umn.uas_43802.model.SearchStoreModel;

public class SearchStoreAdapter extends RecyclerView.Adapter<SearchStoreAdapter.ViewHolder>{
    ArrayList<SearchStoreModel> dataStore;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvToko;
        TextView tvAddress;
        ImageView imgStore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvToko = itemView.findViewById(R.id.nama_toko_search);
            tvAddress = itemView.findViewById(R.id.province);
            imgStore = itemView.findViewById(R.id.image_toko);
        }
    }

    public SearchStoreAdapter (ArrayList<SearchStoreModel> data, Context context) {
        this.dataStore = data;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchStoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_store, parent, false);
        return new SearchStoreAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchStoreAdapter.ViewHolder holder, int position) {
        TextView txtToko = holder.tvToko;
        TextView txtAddress = holder.tvAddress;
        ImageView ivStore = holder.imgStore;
        SearchStoreModel store = dataStore.get(position);

        txtToko.setText(store.getName());
        txtAddress.setText(store.getAddress());

        RequestOptions options = new RequestOptions();
        options.centerInside();

        Glide.with(holder.itemView.getContext()).load(store.getImage()).apply(options).into(ivStore);

//        holder.itemView.setOnClickListener(view -> {
//            Intent intent = new Intent(holder.itemView.getContext(), StoreDetail.class);
//            intent.putExtra("detail", "searchViewToko");
//            intent.putExtra("store", store);
//            holder.itemView.getContext().startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return dataStore.size();
    }
}
