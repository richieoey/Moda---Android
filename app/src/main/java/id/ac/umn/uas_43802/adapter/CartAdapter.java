package id.ac.umn.uas_43802.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.R;
import id.ac.umn.uas_43802.model.Carousel_Page_Product;
import id.ac.umn.uas_43802.model.CartModel;
import id.ac.umn.uas_43802.model.ProductModel;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyHolder> {
    ArrayList<CartModel> data = new ArrayList<>();
    Context context;
    public CartAdapter (ArrayList<CartModel> data, Context context) {
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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Glide.with(context).load(data.get(position).getProduct().getPhotoUrl()).apply(options).into(holder.hero);
            holder.name.setText(data.get(position).getProduct().getName());
            holder.store.setText(data.get(position).getProduct().getToko().get("name").toString());
            holder.price.setText(data.get(position).getProduct().getPrice());
            holder.counter.setText(String.valueOf(data.get(position).getQuantity()));
            holder.plus.setOnClickListener(view -> {
                db.collection("cart").document(user.getUid()).update(user.getUid() + data.get(position).getProduct().getUid() + ".quantity", data.get(position).getQuantity() + 1);
            });

            holder.minus.setOnClickListener(view -> {
                if(data.get(position).getQuantity() == 1){
                    Map<String,Object> updates = new HashMap<>();
                    updates.put(user.getUid() + data.get(position).getProduct().getUid(), FieldValue.delete());

                    DocumentReference docRef = db.collection("cart").document(user.getUid());

                    docRef.update(updates).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d("result", "success");
                            }
                        }
                    });
                }else {
                    db.collection("cart").document(user.getUid()).update(user.getUid() + data.get(position).getProduct().getUid() + ".quantity", data.get(position).getQuantity() - 1);
                }
            });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends  RecyclerView.ViewHolder{
        ImageView hero, minus, plus;
        TextView name, store, price, counter;
        Context context;
        public MyHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            hero = itemView.findViewById(R.id.image_toko);
            name = itemView.findViewById(R.id.namaProduk);
            store = itemView.findViewById(R.id.namaToko);
            price = itemView.findViewById(R.id.harga);
            counter = itemView.findViewById(R.id.counter);
            minus = itemView.findViewById(R.id.minus);
            plus = itemView.findViewById(R.id.plus);
        }
    }
}
