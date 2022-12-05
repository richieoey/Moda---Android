package id.ac.umn.uas_43802;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.databinding.ActivityLoginBinding;
import id.ac.umn.uas_43802.databinding.ActivitySearchProductBinding;
import id.ac.umn.uas_43802.model.ProductModel;

public class SearchProduct extends AppCompatActivity {
    RecyclerView rvProduk;
    ImageView btnBack, btnCart;
    ActivitySearchProductBinding binding;
    ProdukAdapter produkAdapter;
    ArrayList<ProductModel> data = new ArrayList<ProductModel>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =  ActivitySearchProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvProduk = findViewById(R.id.rv_search_product);

        CollectionReference usersRef = db.collection("product");
//        mref = FirebaseDatabase.getInstance().getReference("product");
//        ValueEventListener eventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                populateSearch(snapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//
//        mref.addListenerForSingleValueEvent(eventListener);
                binding.searchView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.searchView.setIconified(false);
                        Task<QuerySnapshot> query = usersRef.whereEqualTo("name", binding.searchView.toString())
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Map<String, Object> hasil = (Map<String, Object>) document.getData();
                                            Log.d("data", hasil.get("category").toString());
                                            HashMap<String, Object> toko = (HashMap<String, Object>) hasil.get("toko");
                                            data.add(new ProductModel(hasil.get("uid").toString(),hasil.get("name").toString(), hasil.get("description").toString(), hasil.get("price").toString(),hasil.get("category").toString() , hasil.get("image").toString() ,toko  ) );
                                        }
                                        produkAdapter = new ProdukAdapter(data);
                                        rvProduk.setAdapter(produkAdapter);
                                    } else {
                                        Log.d("error", "Error getting documents: ", task.getException());
                                    }
                                });
                    }
                });

            binding.backbtn.setOnClickListener(view -> SearchProduct.super.onBackPressed());

            binding.cart.setOnClickListener(v -> {
                startActivity(new Intent(getApplicationContext(), list_cart.class));
            });

    }





    }

//    private void populateSearch(DataSnapshot snapshot) {
//        ArrayList <String> names = new ArrayList<>();
//        if(snapshot.exists()) {
//            for(DataSnapshot ds:snapshot.getChildren()) {
//                String name = ds.child("name").getValue(String.class);
//                names.add(name);
//            }
//
//            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
//            binding.searchView.setAdapter(adapter);
//        } else {
//            Log.d("produks", "Data not found");
//        }
//    }
