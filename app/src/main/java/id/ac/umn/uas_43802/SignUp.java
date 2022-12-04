package id.ac.umn.uas_43802;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Future;

import id.ac.umn.uas_43802.databinding.ActivitySignUpBinding;
import id.ac.umn.uas_43802.utilities.Constants;


public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
	DatePickerDialog datePickerDialog;
	SimpleDateFormat dateFormatter;

	private static final int MY_CAMERA_REQUEST_CODE = 100;

	private ActivitySignUpBinding binding;
	ActivityResultLauncher<Intent> activityResultLauncher;
	private Uri imageUri;

	StorageReference storageReference;
	private FirebaseStorage storage;
	private String docID;
	private FirebaseAuth mAuth;


	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivitySignUpBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		setListeners();
		//preferenceManager = new PreferenceManager(getApplicationContext());

		//Firebase Auth
		mAuth = FirebaseAuth.getInstance();

		// membuat gambar jadi oval
		binding.profile.mutateBackground(true);
		binding.profile.setOval(true);

		binding.spinner.setOnItemSelectedListener(this);
		List<String> categories = new ArrayList<String>();
		categories.add("Male");
		categories.add("Female");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		binding.spinner.setAdapter(dataAdapter);

		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

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

	@RequiresApi(api = Build.VERSION_CODES.M)
	private void setListeners(){
		// Back Button
		binding.backbutton.setOnClickListener(v -> onBackPressed());

		// DATE
		binding.date.setOnClickListener(view -> {
			Calendar newCalendar = Calendar.getInstance();
			datePickerDialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
					Calendar newDate = Calendar.getInstance();
					newDate.set(year, monthOfYear, dayOfMonth);
					binding.date.setText(dateFormatter.format(newDate.getTime()));
				}

			},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
			datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
			datePickerDialog.show();
		});

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
			imageUri = saveImage(bm, SignUp.this);
			binding.profile.setImageURI(imageUri);
			binding.addImageText.setVisibility(View.GONE);
		});


		// Continue Sign Up
		binding.continuesignup.setOnClickListener(view -> {
			if (TextUtils.isEmpty(binding.fullname.getText().toString()) || TextUtils.isEmpty(imageUri.toString()) || TextUtils.isEmpty(binding.email.getText().toString()) || TextUtils.isEmpty(binding.phonenumber.getText().toString()) || TextUtils.isEmpty(binding.password.getText().toString()) ) {
				Toast.makeText(SignUp.this, "Empty field not allowed!", Toast.LENGTH_SHORT).show();
			} else {
				FirebaseFirestore database = FirebaseFirestore.getInstance();
				HashMap<String, String> user = new HashMap<>();

				// Authentication Firebase
				mAuth.createUserWithEmailAndPassword(binding.email.getText().toString(),binding.password.getText().toString())
						.addOnCompleteListener(task -> {
							if(task.isSuccessful() && task.getResult() != null){
								FirebaseUser firebaseUser = task.getResult().getUser();
								if(firebaseUser != null) {
									String uid = firebaseUser.getUid();
									UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
											.setDisplayName(binding.fullname.getText().toString())
											.build();

									firebaseUser.updateProfile(request).addOnCompleteListener(task1 -> {
										user.put("uid", uid);
										user.put(Constants.KEY_NAME, binding.fullname.getText().toString());
										user.put(Constants.KEY_EMAIL, binding.email.getText().toString());
										user.put(Constants.KEY_DATE, binding.date.getText().toString());
										user.put(Constants.KEY_GENDER, binding.spinner.getSelectedItem().toString());

										user.put(Constants.KEY_PHONE, binding.phonenumber.getText().toString());
										user.put(Constants.KEY_PASSWORD, binding.password.getText().toString());
										user.put(Constants.KEY_PIN, "");
										user.put(Constants.KEY_IMAGE, "");

										//Upload Image
										StorageReference fileRef = FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

										fileRef.putFile(imageUri).addOnCompleteListener(tasks -> {
													if (tasks.isSuccessful()) {
														fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
															String url = uri.toString();
															user.put("photoUrl", url);
															database.collection(Constants.KEY_COLLECTION_USERS)
																	.add(user)
																	.addOnSuccessListener(documentReference -> {
																		docID = documentReference.getId();
																		Intent pin = new Intent(SignUp.this, SignUpPin.class);
																		pin.putExtra("key", docID);
																		pin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
																		startActivity(pin);
																	})
																	.addOnFailureListener(exception -> {
																		showToast(exception.getMessage());
																	});
														});
													} else {
														Toast.makeText(this, tasks.getException().getMessage(), Toast.LENGTH_SHORT).show();
													}
												});
									});
								} else{
									showToast("Register gagal");
								}
							} else {
								Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
							}
						});

			}
		});

		// Image
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

	private void reload(){
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
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


	//Menunjukkan toast
	private void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		String item = adapterView.getItemAtPosition(i).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}
