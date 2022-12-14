package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import id.ac.umn.uas_43802.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

	TextView tvSignUp;
	Button SignInBtn, fbBtn, googleBtn, appleBtn;
	private FirebaseAuth mAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvSignUp = findViewById(R.id.signup);
		SignInBtn = findViewById(R.id.signin);
//		fbBtn = findViewById(R.id.facebook);
//		googleBtn = findViewById(R.id.google);
//		appleBtn = findViewById(R.id.apple);

		mAuth = FirebaseAuth.getInstance();

		if(mAuth.getCurrentUser() != null){
			startActivity(new Intent(getApplicationContext(), HomeUser.class));
		}


		tvSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), SignUp.class);
				startActivity(intent);
			}
		});

		SignInBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent signin = new Intent(getApplicationContext(), Login.class);
				startActivity(signin);
			}
		});
	}
}
