package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProdukDetail extends AppCompatActivity {
	ImageView ivProduk;
	TextView tvHarga;
	TextView tvNama;
	ImageView ivBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produk_detail);

		ivProduk = findViewById(R.id.imageView3);
		tvHarga = findViewById(R.id.textView4);
		tvNama = findViewById(R.id.textNama);

		ProdukModel data = getIntent().getParcelableExtra("produk");
		ivProduk.setImageResource(data.getImage());
		tvHarga.setText(data.getHarga());
		tvNama.setText(data.getName());

		ivBack = findViewById(R.id.backHome);
		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ProdukDetail.super.onBackPressed();
			}
		});
	}
}
