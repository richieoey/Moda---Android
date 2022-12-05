package id.ac.umn.uas_43802.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.os.Parcelable;
import android.util.Log;
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

import id.ac.umn.uas_43802.HomeUser;
import id.ac.umn.uas_43802.ProdukDetail;
import id.ac.umn.uas_43802.R;
import id.ac.umn.uas_43802.model.Carousel_Page_Product;
import id.ac.umn.uas_43802.model.ProductModel;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {
    ArrayList<ProductModel> data = new ArrayList<>();
    Context context;
    public ProductAdapter (ArrayList<ProductModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_card, parent , false);

        return new ProductAdapter.MyHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
//        holder.hero.setImageResource(data.get(position).getFeatured_image());
        RequestOptions options = new RequestOptions();
        options.fitCenter();

        Glide.with(context).load(data.get(position).getPhotoUrl()).apply(options).into(holder.hero);
        holder.name.setText(data.get(position).getName());
        holder.store.setText(data.get(position).getToko().get("name").toString());
        holder.price.setText(data.get(position).getPrice());
        holder.itemView.setOnClickListener(view -> {
            ProductModel product = data.get(position);
            Intent intent = new Intent(holder.itemView.getContext(), ProdukDetail.class);
            intent.putExtra("produk", product);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends  RecyclerView.ViewHolder{
        ImageView hero;
        TextView name, store, price;
        Context context;
        public MyHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            hero = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.nameProduct);
            store = itemView.findViewById(R.id.name_store);
            price = itemView.findViewById(R.id.price);
        }
    }
}
