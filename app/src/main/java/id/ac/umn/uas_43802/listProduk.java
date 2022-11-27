package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class listProduk extends AppCompatActivity {

	RecyclerView rV1;
	ProdukAdapter produkAdapter;
	ArrayList<ProdukModel> data;
	ImageView ivBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_produk);

		rV1 = findViewById(R.id.recycler_view1);
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
		rV1.setLayoutManager(layoutManager);

		data = new ArrayList<>();
		for (int i = 0; i < ProdukData.namaProduk.length; i++){
			data.add(new ProdukModel(
					ProdukData.namaProduk[i],
					ProdukData.namaToko[i],
					ProdukData.hargaProduk[i],
					ProdukData.imgProduk[i]
			));

			produkAdapter = new ProdukAdapter(data);
			rV1.setAdapter(produkAdapter);
		}

		ivBack = findViewById(R.id.backHome);
		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listProduk.super.onBackPressed();
			}
		});

	}

}
