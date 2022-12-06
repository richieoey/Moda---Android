package id.ac.umn.uas_43802;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.adapter.ChatDetailAdapter;
import id.ac.umn.uas_43802.adapter.SearchProductAdapter;
import id.ac.umn.uas_43802.databinding.ActivityLoginBinding;
import id.ac.umn.uas_43802.databinding.ActivitySearchProductBinding;
import id.ac.umn.uas_43802.model.ChatDetailModel;
import id.ac.umn.uas_43802.model.ProductModel;
import id.ac.umn.uas_43802.model.SearchProductModel;

public class SearchProduct extends AppCompatActivity {
    RecyclerView rvProduk;
    ActivitySearchProductBinding binding;
    SearchProductAdapter searchProductAdapter;
    ArrayList<SearchProductModel> data = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvProduk = findViewById(R.id.rv_search_product);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvProduk.setLayoutManager(layoutManager);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a reference to the cities collection
        CollectionReference searchRef = db.collection("product");
        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Task<QuerySnapshot> query = searchRef.whereEqualTo("name", s )
                        .get()
                        .addOnCompleteListener(task -> {
                            data.clear();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> hasil = (Map<String, Object>) document.getData();
                                    HashMap<String, Object> toko = (HashMap<String, Object>) hasil.get("toko");
                                    data.add(new SearchProductModel(hasil.get("uid").toString(),hasil.get("name").toString(), hasil.get("description").toString(), hasil.get("price").toString(),hasil.get("category").toString() , hasil.get("image").toString()  ,toko  ));
                                    Log.d("data", hasil.get("name").toString());
                                }
                                searchProductAdapter = new SearchProductAdapter(data, getApplicationContext());
                                rvProduk.setAdapter(searchProductAdapter);
                                Log.d("data", data.toString());
                            } else {
                                Log.d("error", "Error getting documents: ", task.getException());
                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Task<QuerySnapshot> query = searchRef.whereEqualTo("name", s )
                        .get()
                        .addOnCompleteListener(task -> {
                            data.clear();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> hasil = (Map<String, Object>) document.getData();
                                    HashMap<String, Object> toko = (HashMap<String, Object>) hasil.get("toko");
                                    data.add(new SearchProductModel(hasil.get("uid").toString(),hasil.get("name").toString(), hasil.get("description").toString(), hasil.get("price").toString(),hasil.get("category").toString() , hasil.get("image").toString()  ,toko  ));
                                    Log.d("data", hasil.get("name").toString());
                                }
                                searchProductAdapter = new SearchProductAdapter(data, getApplicationContext());
                                rvProduk.setAdapter(searchProductAdapter);
                                Log.d("data", data.toString());
                            } else {
                                Log.d("error", "Error getting documents: ", task.getException());
                            }
                        });

                //Toast.makeText(getApplicationContext(), "Our word : " + s, Toast.LENGTH_SHORT).show();
                Log.d("testing", s);
                return false;
            }
        });

        binding.backbtn.setOnClickListener(view -> SearchProduct.super.onBackPressed());

        binding.cart.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), list_cart.class));
        });

    }





    }