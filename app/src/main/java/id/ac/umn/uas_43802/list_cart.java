package id.ac.umn.uas_43802;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.adapter.CartAdapter;
import id.ac.umn.uas_43802.databinding.ActivityListCartBinding;
import id.ac.umn.uas_43802.model.CartModel;
import id.ac.umn.uas_43802.model.ProductModel;

public class list_cart extends AppCompatActivity {

	private ActivityListCartBinding binding;
	RecyclerView rV;
	CartAdapter cartAdapter;
	ArrayList<ProductModel> cart = new ArrayList<ProductModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityListCartBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		rV = findViewById(R.id.cart_recycler_view);
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
		rV.setLayoutManager(layoutManager);

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		// Create a reference to the cities collection
		CollectionReference cartRef = db.collection("cart");

		Task<DocumentSnapshot> query = cartRef.document(user.getUid())
				.get()
				.addOnCompleteListener(task -> {
					DocumentSnapshot data = task.getResult();
					Map<String, Object> result =  task.getResult().getData();

					for (Map.Entry<String, Object> entry : result.entrySet()) {
						Map<String, Object> hasil = (Map<String, Object>) entry.getValue();
						HashMap<String, Object> produk = (HashMap<String, Object>) hasil.get("product");
						HashMap<String, Object> toko = (HashMap<String, Object>) produk.get("toko");
						cart.add(new ProductModel(produk.get("uid").toString(),produk.get("name").toString(), produk.get("description").toString(), produk.get("price").toString(),produk.get("category").toString() , produk.get("image").toString()  ,toko   ));
						Log.d("cart", toko.toString());
					}

					cartAdapter = new CartAdapter(cart, getApplicationContext());
					rV.setAdapter(cartAdapter);
				});

//		data = new ArrayList<>();
//		for (int i = 0; i <  CartData.nama_toko.length; i++){
//			data.add(new CartModel(
//					CartData.gambar_toko[i],
//					1,
//					CartData.nama_toko[i],
//					CartData.nama_produk[i],
//					CartData.harga_produk[i]
//			));
//		}

		binding.backbutton.setOnClickListener(view -> list_cart.super.onBackPressed());

	}
}
