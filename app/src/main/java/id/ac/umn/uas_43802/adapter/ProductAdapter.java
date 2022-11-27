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
import id.ac.umn.uas_43802.model.Carousel_Page_Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {
    ArrayList<Carousel_Page_Product> data = new ArrayList<>();
    Context context;
    public ProductAdapter (ArrayList<Carousel_Page_Product> data, Context context) {
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
        holder.hero.setImageResource(data.get(position).getFeatured_image());
        holder.name.setText(data.get(position).getName_product());
        holder.store.setText(data.get(position).getName_store());
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
            hero = itemView.findViewById(R.id.product_image);
            name = itemView.findViewById(R.id.nameProduct);
            store = itemView.findViewById(R.id.name_store);
            price = itemView.findViewById(R.id.price);
        }
    }
}
