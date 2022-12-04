package id.ac.umn.uas_43802;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import id.ac.umn.uas_43802.databinding.ActivityListCartBinding;
import id.ac.umn.uas_43802.databinding.ActivityLoginBinding;
import id.ac.umn.uas_43802.databinding.FragmentProfileBinding;
import id.ac.umn.uas_43802.model.ProductModel;

public class list_cart extends AppCompatActivity {

	private ActivityListCartBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityListCartBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		ArrayList<ProductModel> product = new ArrayList<ProductModel>();


		FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		// Create a reference to the cities collection
		CollectionReference cartRef = db.collection("cart");

		Task<DocumentSnapshot> query = cartRef.document(user.getUid())
				.get()
				.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

					@Override
					public void onComplete(@NonNull Task<DocumentSnapshot> task) {
						DocumentSnapshot data = task.getResult();
						Map<String, Object> result =  task.getResult().getData();

						for (Map.Entry<String, Object> entry : result.entrySet()) {
							Map<String, Object> hasil = (Map<String, Object>) entry.getValue();
							HashMap<String, String> produk = (HashMap<String, String>) hasil.get("product");
							String nama = produk.get("name").toString();
							Log.d("cart", produk.get("name").toString());
						}
					}
				});

		binding.backbtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				list_cart.super.onBackPressed();
			}
		});

	}
}
