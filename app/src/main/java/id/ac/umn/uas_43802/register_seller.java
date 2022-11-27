package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class register_seller extends AppCompatActivity {
	ImageView backBtn;
	CardView submit;
	ProgressBar submitBar;
	TextView tvSubmit;
	EditText edStoreName, edAddress, edNoTelp, edKTPname, edKTPnumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_seller);
		backBtn = findViewById(R.id.backbuttonSeller);
		submit = findViewById(R.id.submitBtnSeller);
		tvSubmit = findViewById(R.id.submitText);
		submitBar = findViewById(R.id.submitBar);
		edAddress = findViewById(R.id.alamat);
		edStoreName = findViewById(R.id.storeName);
		edNoTelp = findViewById(R.id.phone);
		edKTPname = findViewById(R.id.ktpname);
		edKTPnumber = findViewById(R.id.ktpnumber);

		submitBar.setVisibility(View.INVISIBLE);
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				register_seller.super.onBackPressed();
			}
		});

		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TextUtils.isEmpty(edStoreName.getText().toString()) || TextUtils.isEmpty(edKTPnumber.getText().toString()) || TextUtils.isEmpty(edKTPname.getText().toString()) || TextUtils.isEmpty(edAddress.getText().toString()) || TextUtils.isEmpty(edNoTelp.getText().toString())  ) {
					Toast.makeText(register_seller.this, "Empty Field Cannot Empty!", Toast.LENGTH_SHORT).show();
				} else {
					tvSubmit.setText("Submitting!");
					submitBar.setVisibility(View.VISIBLE);

					new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
						@Override
						public void run() {
							tvSubmit.setText("Success");
							submit.setCardBackgroundColor(Color.parseColor("#34A853"));
							submitBar.setVisibility(View.INVISIBLE);

							startActivity(new Intent(getApplicationContext(), MainActivity.class));
						}
					}, 4000);
				}
			}
		});
	}

}
