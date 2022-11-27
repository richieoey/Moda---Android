package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpPin extends AppCompatActivity {

	ImageView backBtn;
	ProgressBar submitBar;
	TextView tvSubmit;
	CardView submitCrd;
	EditText ed1, ed2, ed3, ed4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up_pin);

		backBtn = findViewById(R.id.backbutton);
		submitCrd = findViewById(R.id.submitBtn);
		tvSubmit = findViewById(R.id.submitText);
		submitBar = findViewById(R.id.submitBar);
		ed1 = findViewById(R.id.pin1);
		ed2 = findViewById(R.id.pin2);
		ed3 = findViewById(R.id.pin3);
		ed4 = findViewById(R.id.pin4);

		submitBar.setVisibility(View.INVISIBLE);

		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SignUpPin.super.onBackPressed();
			}
		});

		submitCrd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TextUtils.isEmpty(ed1.getText().toString()) || TextUtils.isEmpty(ed2.getText().toString()) || TextUtils.isEmpty(ed3.getText().toString()) || TextUtils.isEmpty(ed4.getText().toString()) ) {
					Toast.makeText(SignUpPin.this, "Pin Cannot Be Empty!", Toast.LENGTH_SHORT).show();
				} else {
					tvSubmit.setText("Submitting!");
					submitBar.setVisibility(View.VISIBLE);

					new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
						@Override
						public void run() {
							tvSubmit.setText("Success");
							submitCrd.setCardBackgroundColor(Color.parseColor("#34A853"));
							submitBar.setVisibility(View.INVISIBLE);

							startActivity(new Intent(getApplicationContext(), MainActivity.class));
						}
					}, 4000);
				}
			}
		});

	}
}
