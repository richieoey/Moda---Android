package id.ac.umn.uas_43802;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class StoreData extends AppCompatActivity {
	private FirebaseFirestore db = FirebaseFirestore.getInstance();
	private List<Store> list = new ArrayList<>();
	private StoreAdapter userAdapter;
	private ProgressDialog progressDialog;
//	static  int[] store = {
//			R.drawable.store1, R.drawable.anjay_store, R.drawable.sikok_store, R.drawable.mami_store, R.drawable.online, R.drawable.solusi_murah
//	};
//
//
//	static String[] nameStore= {
//			"Slebew Store", "Anjay Store", "SikokBagiDuo Store", "Mami Store", "Online Shop", "Solusi Murah"
//	};
@Override
protected void onStart() {
	super.onStart();
	getData();
}

	private void getData(){
		progressDialog.show();
		db.collection("toko")
				.get()
				.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
					@SuppressLint("NotifyDataSetChanged")
					@Override
					public void onComplete(@NonNull Task<QuerySnapshot> task) {
						list.clear();
						if (task.isSuccessful()){
							for (QueryDocumentSnapshot document : task.getResult()){
								String[] nameStore = {
										document.getString("name")
								};
							}
							userAdapter.notifyDataSetChanged();
						}else{
							Toast.makeText(getApplicationContext(), "Data gagal di ambil!", Toast.LENGTH_SHORT).show();
						}
						progressDialog.dismiss();
					}
				});
	}

}
