package id.ac.umn.uas_43802;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import id.ac.umn.uas_43802.databinding.ActivityLoginBinding;
import id.ac.umn.uas_43802.utilities.Constants;


public class Login extends AppCompatActivity {

	private ActivityLoginBinding binding;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = ActivityLoginBinding.inflate(getLayoutInflater());
		setContentView(binding.getRoot());
		binding.progressBar.setVisibility(View.INVISIBLE);
		// ...
		// Initialize Firebase Auth
		mAuth = FirebaseAuth.getInstance();

		binding.backLogin.setOnClickListener(view -> Login.super.onBackPressed());

		binding.loginBtn.setOnClickListener(view -> {
			if (TextUtils.isEmpty(binding.emailLogin.getText().toString()) || TextUtils.isEmpty(binding.passLogin.getText().toString()) ) {
				Toast.makeText(Login.this, "Username/Password Cannot Be Empty!", Toast.LENGTH_SHORT).show();
			} else {
				binding.loginText.setText("Submitting!");
				binding.progressBar.setVisibility(View.VISIBLE);

				new Handler(Looper.getMainLooper()).postDelayed(() -> {
					mAuth.signInWithEmailAndPassword(binding.emailLogin.getText().toString(),binding.passLogin.getText().toString())
							.addOnCompleteListener(task -> {
								if(task.isSuccessful() && task.getResult()!=null){
									if(task.getResult().getUser() != null){
										reload();
									} else {
										showToast("Login gagal");
									}
								} else {
									binding.progressBar.setVisibility(View.INVISIBLE);
									binding.loginText.setText("Login");
									Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
								}
							});

				}, 1000);
			}
		});

	}
	//Menunjukkan toast
	private void showToast(String message){
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}

	private void reload(){
		startActivity(new Intent(getApplicationContext(), HomeUser.class));
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
}
