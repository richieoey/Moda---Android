package id.ac.umn.uas_43802;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.adapter.CartAdapter;
import id.ac.umn.uas_43802.adapter.HistoryAdapter;
import id.ac.umn.uas_43802.databinding.ActivityHistoryBinding;
import id.ac.umn.uas_43802.databinding.ActivitySignUpBinding;
import id.ac.umn.uas_43802.model.CartModel;
import id.ac.umn.uas_43802.model.ProductModel;

public class History extends AppCompatActivity {

    ActivityHistoryBinding binding;
    ArrayList<CartModel> products = new ArrayList<>();
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
        binding.cartRecyclerView.setLayoutManager(layoutManager);
        historyAdapter = new HistoryAdapter(products, getApplicationContext());
        binding.cartRecyclerView.setAdapter(historyAdapter);

        CollectionReference cartRef = db.collection("history");

        DocumentReference query = cartRef.document(user.getUid());
        query.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("test", "Listen failed.", e);
                    return;
                }

                String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";
                products.clear();
                if (snapshot != null && snapshot.exists()) {
                    Map<String, Object> result =  snapshot.getData();
                    ArrayList<HashMap<String, Object>> product = (ArrayList<HashMap<String, Object>>) result.get("products");
                    for(int i=0; i < product.size(); i++){
                        HashMap<String, Object> produk = (HashMap<String, Object>) product.get(i).get("product");
                        HashMap<String, Object> toko = (HashMap<String, Object>) produk.get("toko");
                        products.add(new CartModel(new ProductModel(produk.get("uid").toString(),produk.get("name").toString(), produk.get("description").toString(), produk.get("price").toString(),produk.get("category").toString() , produk.get("image").toString()  ,toko), (int) (long) product.get(i).get("quantity")));
                    }
                    historyAdapter = new HistoryAdapter(products, getApplicationContext());
                    binding.cartRecyclerView.setAdapter(historyAdapter);
                } else {
                    Log.d("test", source + " data: null");
                }
            }
        });


        binding.backbutton.setOnClickListener(view -> History.super.onBackPressed() );

    }
}