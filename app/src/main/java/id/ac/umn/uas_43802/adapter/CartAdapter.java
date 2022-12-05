package id.ac.umn.uas_43802.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import id.ac.umn.uas_43802.R;
import id.ac.umn.uas_43802.model.Carousel_Page_Product;
import id.ac.umn.uas_43802.model.CartModel;
import id.ac.umn.uas_43802.model.ProductModel;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {
    ArrayList<ProductModel> data = new ArrayList<>();
    Context context;
    public CartAdapter (ArrayList<ProductModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item_list, parent , false);
        return new CartAdapter.MyHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyHolder holder, int position) {
        RequestOptions options = new RequestOptions();
        options.circleCrop();
            Glide.with(context).load(data.get(position).getPhotoUrl()).apply(options).into(holder.hero);
            holder.name.setText(data.get(position).getName());
            holder.store.setText(data.get(position).getToko().get("name").toString());
            holder.price.setText(data.get(position).getPrice());
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
            hero = itemView.findViewById(R.id.image_toko);
            name = itemView.findViewById(R.id.namaProduk);
            store = itemView.findViewById(R.id.namaToko);
            price = itemView.findViewById(R.id.harga);
        }
    }
}
