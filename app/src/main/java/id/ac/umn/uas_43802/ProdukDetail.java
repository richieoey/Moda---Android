package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umn.uas_43802.model.ProductModel;
import id.ac.umn.uas_43802.model.SearchProductModel;

public class ProdukDetail extends AppCompatActivity {
	ImageView ivProduk, addChat;
	TextView tvHarga, description;
	TextView tvNama;
	ImageView ivBack;
	Button addCart;
	ProductModel data;
	String combineId;
	ImageButton cart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_produk_detail);

		ivProduk = findViewById(R.id.imageView3);
		tvHarga = findViewById(R.id.textView4);
		tvNama = findViewById(R.id.textNama);
		addCart = findViewById(R.id.addCart);
		addChat = findViewById(R.id.addChat);
		cart = findViewById(R.id.cart_list);
		description = findViewById(R.id.description);

		FirebaseFirestore db = FirebaseFirestore.getInstance();
		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

		String bundle = getIntent().getStringExtra("uid");

		// Mengecek asal intent dari hasil search product atau dari product home/kategori
			RequestOptions options = new RequestOptions();
			options.fitCenter();

			db.collection("product").document(bundle)
					.get()
					.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
						@Override
						public void onSuccess(DocumentSnapshot documentSnapshot) {
							Map<String,Object> hasil =  documentSnapshot.getData();
							HashMap<String,Object> toko = (HashMap<String,Object>) hasil.get("toko");

							data = new ProductModel(hasil.get("uid").toString(), hasil.get("name").toString(), hasil.get("description").toString(), hasil.get("price").toString(),hasil.get("category").toString() , hasil.get("image").toString() , toko);

							Glide.with(ProdukDetail.this).load(data.getPhotoUrl()).apply(options).into(ivProduk);
							tvHarga.setText(data.getPrice());
							tvNama.setText(data.getName());
							description.setText(data.getDescription());

							if(user.getUid().compareTo(data.getToko().get("uid").toString()) == 0 ){
								addChat.setVisibility(View.GONE);
							}

							addCart.setOnClickListener(view -> {
								HashMap<String, Object> product = new HashMap<String, Object>();
								HashMap<String, Object> sendData = new HashMap<String, Object>();
								HashMap<String, Object> finalData = new HashMap<String, Object>();
								product.put("uid", data.getUid());
								product.put("name", data.getName());
								product.put("description", data.getDescription());
								product.put("category", data.getCategory());
								product.put("price", data.getPrice());
								product.put("image", data.getPhotoUrl());
								product.put("toko", data.getToko());
								sendData.put("product", product);
								sendData.put("quantity", 1);
								finalData.put(user.getUid() + data.getUid(), sendData);
								db.collection("cart").document(user.getUid()).update(finalData)
										.addOnSuccessListener(new OnSuccessListener<Void>() {
											@Override
											public void onSuccess(Void unused) {
												Log.d("result", "berhasil");
											}
										});
							});

							addChat.setOnClickListener(view -> {
								if(user.getUid().compareTo(data.getToko().get("uid").toString()) > 0){
									combineId = user.getUid() + data.getToko().get("uid");
								}else{
									combineId =  data.getToko().get("uid") + user.getUid();
								}

								HashMap<String, Object> sendData = new HashMap<>();
								HashMap<String, Object> lastMessage = new HashMap<>();
								HashMap<String, Object> userInfo = new HashMap<>();

								lastMessage.put("text", "");
								userInfo.put("displayName", data.getToko().get("name"));
								userInfo.put("photoURL", data.getToko().get("photoURL"));
								userInfo.put("uid", data.getToko().get("uid"));

								sendData.put("date", Timestamp.now());
								sendData.put("lastMessage", lastMessage);
								sendData.put("userInfo", userInfo);


								db.collection("userChats").document(user.getUid()).update(combineId, sendData)
										.addOnSuccessListener(new OnSuccessListener<Void>() {
											@Override
											public void onSuccess(Void unused) {
												Log.d("hasil", "sukses");
											}
										});

								sendData.clear();
								userInfo.clear();

								userInfo.put("displayName", user.getDisplayName());
								userInfo.put("photoURL", user.getPhotoUrl());
								userInfo.put("uid", user.getUid());

								sendData.put("date", Timestamp.now());
								sendData.put("lastMessage", lastMessage);
								sendData.put("userInfo", userInfo);

								db.collection("userChats").document(data.getToko().get("uid").toString()).update(combineId, sendData)
										.addOnSuccessListener(new OnSuccessListener<Void>() {
											@Override
											public void onSuccess(Void unused) {
												Log.d("hasil", "sukses");
											}
										});

								HashMap<String, Object> chats = new HashMap<>();

								chats.put("messages", new ArrayList<>());

								db.collection("chats").document(combineId).set(chats)
										.addOnSuccessListener(new OnSuccessListener<Void>() {
											@Override
											public void onSuccess(Void unused) {
												Log.d("chats", "sukses");
											}
										});

							});


						}
					});

		cart.setOnClickListener(view -> {
			Intent intent = new Intent(ProdukDetail.this, list_cart.class);
			startActivity(intent);
		});

		ivBack = findViewById(R.id.backHome);
		ivBack.setOnClickListener(view -> ProdukDetail.super.onBackPressed());
	}
}
