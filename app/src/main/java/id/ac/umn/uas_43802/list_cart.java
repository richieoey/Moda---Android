package id.ac.umn.uas_43802;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.adapter.CartAdapter;
import id.ac.umn.uas_43802.adapter.ChatAdapter;
import id.ac.umn.uas_43802.databinding.ActivityListCartBinding;
import id.ac.umn.uas_43802.model.CartModel;
import id.ac.umn.uas_43802.model.ChatModel;
import id.ac.umn.uas_43802.model.ProductModel;

public class list_cart extends AppCompatActivity {

	private ActivityListCartBinding binding;
	RecyclerView rV;
	CartAdapter cartAdapter;
	ArrayList<CartModel> cart = new ArrayList<CartModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityListCartBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		rV = findViewById(R.id.cart_recycler_view);
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
		rV.setLayoutManager(layoutManager);
		cartAdapter = new CartAdapter(cart, getApplicationContext());
		rV.setAdapter(cartAdapter);

		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		// Create a reference to the cities collection
		CollectionReference cartRef = db.collection("cart");

		DocumentReference query = cartRef.document(user.getUid());
				query.addSnapshotListener(new EventListener<DocumentSnapshot>() {
					@Override
					public void onEvent(@Nullable DocumentSnapshot snapshot,
										@Nullable FirebaseFirestoreException e) {
						if (e != null) {
							Log.w("test", "Listen failed.", e);
							return;
						}

						String source = snapshot != null && snapshot.getMetadata().hasPendingWrites()
								? "Local" : "Server";
						cart.clear();
						if (snapshot != null && snapshot.exists()) {
							Map<String, Object> result =  snapshot.getData();
							if(!result.entrySet().isEmpty()){
								for (Map.Entry<String, Object> entry : result.entrySet()) {
									Map<String, Object> hasil = (Map<String, Object>) entry.getValue();
									HashMap<String, Object> produk = (HashMap<String, Object>) hasil.get("product");
									HashMap<String, Object> toko = (HashMap<String, Object>) produk.get("toko");

									cart.add(new CartModel(new ProductModel(produk.get("uid").toString(),produk.get("name").toString(), produk.get("description").toString(), produk.get("price").toString(),produk.get("category").toString() , produk.get("image").toString()  ,toko), (int) (long) hasil.get("quantity")));
								}
							}
							cartAdapter = new CartAdapter(cart, getApplicationContext());
							rV.setAdapter(cartAdapter);
						} else {
							Log.d("test", source + " data: null");
						}
					}
				});
//

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
