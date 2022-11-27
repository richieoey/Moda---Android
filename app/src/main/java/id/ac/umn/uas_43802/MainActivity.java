package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	TextView tvSignUp;
	Button SignInBtn, fbBtn, googleBtn, appleBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvSignUp = findViewById(R.id.signup);
		SignInBtn = findViewById(R.id.signin);
		fbBtn = findViewById(R.id.facebook);
		googleBtn = findViewById(R.id.google);
		appleBtn = findViewById(R.id.apple);


		tvSignUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(MainActivity.this, SignUp.class);
				startActivity(intent);
			}
		});

		SignInBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent signin = new Intent(MainActivity.this, Login.class);
				startActivity(signin);
			}
		});
	}
}
