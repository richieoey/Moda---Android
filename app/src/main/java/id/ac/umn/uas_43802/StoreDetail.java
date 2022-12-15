package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.adapter.ProductAdapter;
import id.ac.umn.uas_43802.databinding.ActivityLoginBinding;
import id.ac.umn.uas_43802.databinding.ActivityStoreDetailBinding;
import id.ac.umn.uas_43802.model.ProductModel;

public class StoreDetail extends AppCompatActivity {
    ImageView btnBack, btnCart, imGambarStore;
    TextView tvNameToolbar, tvStoreName, tvProvince;
    RecyclerView rv;
    CardView btnChatPenjual;
    ActivityStoreDetailBinding binding;
    private LinearLayoutManager linearLayoutManager;
    private ProductAdapter productAdapter;
    private ArrayList<ProductModel> product = new ArrayList<ProductModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        StoreModel data = getIntent().getParcelableExtra("store");

        btnBack = findViewById(R.id.backbtn_store_detail);
        btnCart = findViewById(R.id.add_cart_toko);
        imGambarStore = findViewById(R.id.image_store_detail);
        tvNameToolbar = findViewById(R.id.toolbar_nama_toko);
        tvStoreName = findViewById(R.id.store_name_detail);
        tvProvince = findViewById(R.id.store_name_lokasi);
        rv = findViewById(R.id.recycler_view1);
        btnChatPenjual = findViewById(R.id.btn_chat_penjual);

        tvStoreName.setText(data.getName());
        tvNameToolbar.setText(data.getName());

        RequestOptions options = new RequestOptions();
        options.circleCrop();
        Glide.with(getApplicationContext()).load(data.getImage()).apply(options).into(imGambarStore);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a reference to the cities collection
        CollectionReference productRef =  db.collection("product");

        Task<QuerySnapshot> query = productRef.whereEqualTo("toko.name", data.getName())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> hasil = (Map<String, Object>) document.getData();
                            HashMap<String, Object> toko = (HashMap<String, Object>) hasil.get("toko");
                            product.add(new ProductModel(hasil.get("uid").toString(), hasil.get("name").toString(), hasil.get("description").toString(), hasil.get("price").toString(),hasil.get("category").toString() , hasil.get("image").toString() ,toko));
                        }
                        productAdapter = new ProductAdapter(product, getApplicationContext());
                        rv.setAdapter(productAdapter);
                    } else {
                        Log.d("error", "Error getting documents: ", task.getException());
                    }
                });

        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(linearLayoutManager);
        productAdapter = new ProductAdapter(product, getApplicationContext());
        rv.setAdapter(productAdapter);

        btnBack.setOnClickListener(view -> {
            StoreDetail.super.onBackPressed();
        });
    }
}