package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import id.ac.umn.uas_43802.model.ProductModel;

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

		ProductModel data = getIntent().getParcelableExtra("produk");
		RequestOptions options = new RequestOptions();
		options.circleCrop();

		Glide.with(getApplicationContext()).load(data.getPhotoUrl()).apply(options).into(ivProduk);
		tvHarga.setText(data.getPrice());
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
