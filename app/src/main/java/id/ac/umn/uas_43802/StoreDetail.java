package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StoreDetail extends AppCompatActivity {
    ImageView btnBack, btnCart, imGambarStore;
    TextView tvNameToolbar, tvStoreName, tvProvince;
    RecyclerView rvProduk;
    CardView btnChatPenjual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
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