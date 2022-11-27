package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

	ImageView backBtn;
	EditText edEmail, edPassword;
	ProgressBar progres;
	TextView tvLogin;
	CardView loginCrd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		backBtn = findViewById(R.id.backLogin);
		edEmail = findViewById(R.id.emailLogin);
		edPassword = findViewById(R.id.passLogin);
		progres = findViewById(R.id.progressBar);
		tvLogin = findViewById(R.id.loginText);
		loginCrd = findViewById(R.id.loginBtn);

		progres.setVisibility(View.INVISIBLE);

		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Login.super.onBackPressed();
			}
		});

		loginCrd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TextUtils.isEmpty(edEmail.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString()) ) {
					Toast.makeText(Login.this, "Username/Password Cannot Be Empty!", Toast.LENGTH_SHORT).show();
				} else {
					tvLogin.setText("Submitting!");
					progres.setVisibility(View.VISIBLE);

					new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
						@Override
						public void run() {
							tvLogin.setText("Success");
							loginCrd.setCardBackgroundColor(Color.parseColor("#34A853"));
							progres.setVisibility(View.INVISIBLE);

							startActivity(new Intent(getApplicationContext(), HomeUser.class));
						}
					}, 4000);
				}
			}
		});


	}
}
