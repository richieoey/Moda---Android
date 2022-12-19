package id.ac.umn.uas_43802;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Objects;

import id.ac.umn.uas_43802.databinding.ActivityRegisterSellerBinding;
import id.ac.umn.uas_43802.utilities.Constants;

public class register_seller extends AppCompatActivity {
	ActivityResultLauncher<Intent> activityResultLauncher;
	private Uri imageUri;
	private ActivityRegisterSellerBinding binding;
	private FirebaseAuth mAuth;


	private static final int MY_CAMERA_REQUEST_CODE = 100;


	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityRegisterSellerBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());

		setListeners();

		//Firebase Auth
		mAuth = FirebaseAuth.getInstance();



	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	private void setListeners() {
		//membuat gambar menjadi oval
		binding.profile.mutateBackground(true);
		binding.profile.setOval(true);

		binding.submitBar.setVisibility(View.INVISIBLE);

		binding.backbuttonSeller.setOnClickListener(view -> register_seller.super.onBackPressed());

		// CAMERA INTENT
		binding.cameraBtn.setOnClickListener(view -> {
			if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
			}
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			activityResultLauncher.launch(takePictureIntent);
		});
		activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
			Bundle extras = result.getData().getExtras();
			Bitmap imageBitmap = (Bitmap) extras.get("data");

			WeakReference<Bitmap> result1 = new WeakReference<>(Bitmap.createScaledBitmap(imageBitmap,
					imageBitmap.getHeight(),imageBitmap.getWidth(), false).copy(
					Bitmap.Config.RGB_565,true ));

			Bitmap bm = result1.get();
			imageUri = saveImage(bm, register_seller.this);
			binding.profile.setImageURI(imageUri);
			binding.addImageText.setVisibility(View.GONE);
		});

		// Submit
		binding.submitBtnSeller.setOnClickListener(view -> {
			if (TextUtils.isEmpty(binding.storeName.getText().toString()) || TextUtils.isEmpty(binding.ktpnumber.getText().toString()) || TextUtils.isEmpty(binding.ktpname.getText().toString()) || TextUtils.isEmpty(binding.alamat.getText().toString()) || TextUtils.isEmpty(binding.phone.getText().toString())  ) {
				Toast.makeText(register_seller.this, "Empty Field Cannot Empty!", Toast.LENGTH_SHORT).show();
			} else {
				FirebaseFirestore database = FirebaseFirestore.getInstance();
				HashMap<String, Object> store = new HashMap<>();
				String uid = mAuth.getCurrentUser().getUid();

				store.put("uid", uid);
				store.put("address", binding.alamat.getText().toString());
				store.put("ktpName", binding.ktpname.getText().toString());
				store.put("ktpNumber", binding.ktpnumber.getText().toString());
				store.put("name", binding.storeName.getText().toString());
				store.put("phoneNumber", binding.phone.getText().toString());
				store.put("province", binding.province.getText().toString());

				//Upload Image
				StorageReference fileRef = FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

				fileRef.putFile(imageUri).addOnCompleteListener(tasks -> {
					if (tasks.isSuccessful()) {
						fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
							String url = uri.toString();
							store.put("image", url);
							database.collection("toko")
									.add(store)
									.addOnSuccessListener(documentReference -> {

										Intent intent = new Intent(register_seller.this, HomeUser.class);
										startActivity(intent);
									})
									.addOnFailureListener(exception -> {
										showToast(exception.getMessage());
									});

							database.collection("user").whereEqualTo("uid", uid)
									.get()
									.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
										@Override
										public void onComplete(@NonNull Task<QuerySnapshot> task) {
											if (task.isSuccessful()) {
												for (QueryDocumentSnapshot document : task.getResult()) {
													database.collection("user").document(document.getId()).update("regSeller", true);
//													Log.d(TAG, document.getId() + " => " + document.getData());
												}
											} else {
												Log.d("error", "Error getting documents: ", task.getException());
											}
										}
									});
//									.addOnSuccessListener(queryDocumentSnapshots -> {
//
//										Log.d("seller", queryDocumentSnapshots.getDocuments().toString());
//										Log.d("user", uid);
//										database.collection("user").document(queryDocumentSnapshots.getDocuments().toString()).update("regSeller", true);
//									});


						});
					} else {
						Toast.makeText(this, tasks.getException().getMessage(), Toast.LENGTH_SHORT).show();
					}
				});



				binding.submitText.setText("Submitting!");
				binding.submitBar.setVisibility(View.VISIBLE);

				new Handler(Looper.getMainLooper()).postDelayed(() -> {
					binding.submitText.setText("Success");
					binding.submitBtnSeller.setCardBackgroundColor(Color.parseColor("#34A853"));
					binding.submitBar.setVisibility(View.INVISIBLE);

					startActivity(new Intent(getApplicationContext(), MainActivity.class));
				}, 4000);
			}
		});

		//Pick Image
		binding.layoutImage.setOnClickListener(v -> {
			Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			pickImage.launch(intent);
		});
	}

	private String getFileExtension (Uri uri){
		ContentResolver contentResolver = getContentResolver();

		MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
		return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
	}

	private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
			new ActivityResultContracts.StartActivityForResult(), result -> {
				if(result.getResultCode() == RESULT_OK) {
					if(result.getData() != null){
						imageUri = result.getData().getData();
						binding.profile.setImageURI(imageUri);
						binding.addImageText.setVisibility(View.GONE);
					}
				}
			}
	);

	private Uri saveImage(Bitmap image, Context context) {
		File imagesFolder = new File(context.getCacheDir(), "images");
		Uri uri = null;
		try {
			imagesFolder.mkdirs();
			File file = new File(imagesFolder, System.currentTimeMillis() + ".jpg");
			FileOutputStream stream = new FileOutputStream(file);
			image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			stream.flush();
			stream.close();
			uri = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()), BuildConfig.APPLICATION_ID + ".provider", file);
		} catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uri;
	}

	@Override
	public void onStart() {
		super.onStart();
		// Check if user is signed in (non-null) and update UI accordingly.
		FirebaseUser currentUser = mAuth.getCurrentUser();
		if(currentUser != null){
			//reload();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == MY_CAMERA_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
			}
		}
	}

	private void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}


}