package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

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

import id.ac.umn.uas_43802.adapter.SearchProductAdapter;
import id.ac.umn.uas_43802.adapter.SearchStoreAdapter;
import id.ac.umn.uas_43802.databinding.ActivitySearchProductBinding;
import id.ac.umn.uas_43802.databinding.ActivitySearchStoreBinding;
import id.ac.umn.uas_43802.model.SearchProductModel;
import id.ac.umn.uas_43802.model.SearchStoreModel;

public class SearchStore extends AppCompatActivity {
    RecyclerView rvStore;
    ActivitySearchStoreBinding binding;
    SearchStoreAdapter searchStoreAdapter;
    ArrayList<SearchStoreModel> data = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchStoreBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        rvStore = findViewById(R.id.rv_search_store);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        rvStore.setLayoutManager(layoutManager);

        searchStoreAdapter = new SearchStoreAdapter(data, getApplicationContext());
        rvStore.setAdapter(searchStoreAdapter);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Create a reference to the cities collection
        CollectionReference searchRef = db.collection("toko");

        binding.searchStore.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Task<QuerySnapshot> query = searchRef.whereEqualTo("name", s)
                        .get()
                        .addOnCompleteListener(task -> {
                            data.clear();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> hasil = (Map<String, Object>) document.getData();
                                    data.add(new SearchStoreModel(hasil.get("address").toString(), hasil.get("image").toString(), hasil.get("ktpName").toString(), hasil.get("ktpNumber").toString(), hasil.get("name").toString(), hasil.get("phoneNumber").toString(), hasil.get("province").toString(), hasil.get("uid").toString()));
                                    Log.d("data", hasil.get("name").toString());
                                }
                                searchStoreAdapter = new SearchStoreAdapter(data, getApplicationContext());
                                rvStore.setAdapter(searchStoreAdapter);
                                Log.d("data", data.toString());
                            } else {
                                Log.d("error", "Error getting documents: ", task.getException());
                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Task<QuerySnapshot> query = searchRef.whereEqualTo("name", s)
                        .get()
                        .addOnCompleteListener(task -> {
                            data.clear();
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> hasil = (Map<String, Object>) document.getData();
                                    data.add(new SearchStoreModel(hasil.get("address").toString(), hasil.get("image").toString(), hasil.get("ktpName").toString(), hasil.get("ktpNumber").toString(), hasil.get("name").toString(), hasil.get("phoneNumber").toString(), hasil.get("province").toString(), hasil.get("uid").toString()));
                                    Log.d("data", hasil.get("image").toString());
                                }
                                searchStoreAdapter = new SearchStoreAdapter(data, getApplicationContext());
                                rvStore.setAdapter(searchStoreAdapter);
                            } else {
                                Log.d("error", "Error getting documents: ", task.getException());
                            }
                        });
                    Log.d("testing", s);
                return false;
            }
        });

        binding.backbtn.setOnClickListener(view -> SearchStore.super.onBackPressed());

        binding.cart.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), list_cart.class));
        });

    }




}