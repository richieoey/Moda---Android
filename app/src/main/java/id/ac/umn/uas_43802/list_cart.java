package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class list_cart extends AppCompatActivity {

	ImageView rounded;
	ImageView ivPlus;
	ImageView ivMinus;
	TextView tvCounter;
	ImageView ivBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_cart);
		rounded = (ImageView) findViewById(R.id.rounded);
		rounded.setBackgroundResource(R.drawable.rounded);

		ivPlus = findViewById(R.id.plus);
		ivMinus = findViewById(R.id.minus);
		tvCounter = findViewById(R.id.counter);
		ivBack = findViewById(R.id.backbtn);

		ivPlus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String str = tvCounter.getText().toString();
				Integer counter = Integer.valueOf(str);
				counter = counter + 1;
				String strCounter = counter.toString();
				tvCounter.setText(strCounter);
			}
		});

		ivMinus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String str = tvCounter.getText().toString();
				Integer counter = Integer.valueOf(str);
				counter = counter - 1;
				String strCounter = counter.toString();
				tvCounter.setText(strCounter);
			}
		});

		ivBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				list_cart.super.onBackPressed();
			}
		});
	}
}
