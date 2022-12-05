package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SearchStore extends AppCompatActivity {
    RecyclerView rvProduk;
    ImageView btnBack, btnCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_store);

        rvProduk = findViewById(R.id.rv_search_store);
        btnBack = findViewById(R.id.backbtn);
        btnCart = findViewById(R.id.cart);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchStore.super.onBackPressed();
            }
        });
    }
}