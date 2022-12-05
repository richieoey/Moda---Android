package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import id.ac.umn.uas_43802.databinding.ActivityLoginBinding;
import id.ac.umn.uas_43802.databinding.ActivityStoreDetailBinding;

public class StoreDetail extends AppCompatActivity {
    ImageView btnBack, btnCart, imGambarStore;
    TextView tvNameToolbar, tvStoreName, tvProvince;
    RecyclerView rvProduk;
    CardView btnChatPenjual;
    ActivityStoreDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStoreDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btnBack = findViewById(R.id.backbtn_store_detail);
        btnCart = findViewById(R.id.add_cart_toko);
        imGambarStore = findViewById(R.id.image_store_detail);
        tvNameToolbar = findViewById(R.id.toolbar_nama_toko);
        tvStoreName = findViewById(R.id.store_name_detail);
        tvProvince = findViewById(R.id.store_name_lokasi);
        rvProduk = findViewById(R.id.recycler_view1);
        btnChatPenjual = findViewById(R.id.btn_chat_penjual);
    }
}