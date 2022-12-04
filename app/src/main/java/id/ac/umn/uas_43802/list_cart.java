package id.ac.umn.uas_43802;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.ac.umn.uas_43802.adapter.CartAdapter;
import id.ac.umn.uas_43802.databinding.ActivityListCartBinding;
import id.ac.umn.uas_43802.model.CartModel;

public class list_cart extends AppCompatActivity {

	private ActivityListCartBinding binding;
	RecyclerView rV;
	CartAdapter cartAdapter;
	ArrayList<CartModel> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityListCartBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		ArrayList<CartModel> cart = new ArrayList<CartModel>();


//		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//		FirebaseFirestore db = FirebaseFirestore.getInstance();
//		// Create a reference to the cities collection
//		CollectionReference cartRef = db.collection("cart");

//		Task<DocumentSnapshot> query = cartRef.document(user.getUid())
//				.get()
//				.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//
//					@Override
//					public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//						DocumentSnapshot data = task.getResult();
//						Map<String, Object> result =  task.getResult().getData();
//
//						for (Map.Entry<String, Object> entry : result.entrySet()) {
//							Map<String, Object> hasil = (Map<String, Object>) entry.getValue();
//							HashMap<String, String> produk = (HashMap<String, String>) hasil.get("product");
//							String nama = produk.get("name").toString();
//							Log.d("cart", produk.get("name").toString());
//						}
//					}
//				});

		rV = findViewById(R.id.cart_recycler_view);
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
		rV.setLayoutManager(layoutManager);

		data = new ArrayList<>();
		for (int i = 0; i <  CartData.nama_toko.length; i++){
			data.add(new CartModel(
					CartData.gambar_toko[i],
					1,
					CartData.nama_toko[i],
					CartData.nama_produk[i],
					CartData.harga_produk[i]
			));

			cartAdapter = new CartAdapter(data, getApplicationContext());
			rV.setAdapter(cartAdapter);
		}

		binding.backbutton.setOnClickListener(view -> list_cart.super.onBackPressed());

	}
}
