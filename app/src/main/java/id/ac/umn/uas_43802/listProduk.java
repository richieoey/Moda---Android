package id.ac.umn.uas_43802;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
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

import id.ac.umn.uas_43802.model.ProductModel;

public class listProduk extends AppCompatActivity {

	RecyclerView rV1;
	ProdukAdapter produkAdapter;
	ArrayList<ProductModel> data = new ArrayList<ProductModel>();
	ImageView ivBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_produk);

		rV1 = findViewById(R.id.recycler_view1);
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
		rV1.setLayoutManager(layoutManager);
		produkAdapter = new ProdukAdapter(data);
		rV1.setAdapter(produkAdapter);
		String type = getIntent().getStringExtra("type");

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		// Create a reference to the cities collection
		CollectionReference usersRef = db.collection("product");
		Log.d("data", type);
		Task<QuerySnapshot> query = usersRef.whereEqualTo("category", type).limit(10)
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@Override
					public void onComplete(@NonNull Task<QuerySnapshot> task) {
						if (task.isSuccessful()) {
							for (QueryDocumentSnapshot document : task.getResult()) {
								Map<String, Object> hasil = (Map<String, Object>) document.getData();
								Log.d("data", hasil.get("category").toString());
								HashMap<String, Object> toko = (HashMap<String, Object>) hasil.get("toko");
								data.add(new ProductModel(hasil.get("uid").toString(),hasil.get("name").toString(), hasil.get("description").toString(), hasil.get("price").toString(),hasil.get("category").toString() , hasil.get("image").toString()  ,toko   ) );
							}
							produkAdapter = new ProdukAdapter(data);
							rV1.setAdapter(produkAdapter);
						} else {
							Log.d("error", "Error getting documents: ", task.getException());
						}
					}
				});
//		data = new ArrayList<>();
//		for (int i = 0; i < ProdukData.namaProduk.length; i++){
//			data.add(new ProdukModel(
//					ProdukData.namaProduk[i],
//					ProdukData.namaToko[i],
//					ProdukData.hargaProduk[i],
//					ProdukData.imgProduk[i]
//			));
//
//		}

		ivBack = findViewById(R.id.backHome);
		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listProduk.super.onBackPressed();
			}
		});

	}

}
