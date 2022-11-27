package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
	ImageView backBtn;
	DatePickerDialog datePickerDialog;
	SimpleDateFormat dateFormatter;
	TextView tvDate;
	Spinner spinner;
	Button continueBtn;
	EditText edNama, edEmail, edPhone, edPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		spinner = findViewById(R.id.spinner);
		backBtn = findViewById(R.id.backbutton);
		tvDate = findViewById(R.id.date);
		continueBtn = findViewById(R.id.continuesignup);
		edNama = findViewById(R.id.fullname);
		edEmail = findViewById(R.id.email);
		edPassword = findViewById(R.id.password);
		edPhone = findViewById(R.id.phonenumber);

		spinner.setOnItemSelectedListener(this);
		List<String> categories = new ArrayList<String>();
		categories.add("Male");
		categories.add("Female");

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(dataAdapter);

		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SignUp.super.onBackPressed();
			}
		});


		tvDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Calendar newCalendar = Calendar.getInstance();
				datePickerDialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						Calendar newDate = Calendar.getInstance();
						newDate.set(year, monthOfYear, dayOfMonth);
						tvDate.setText(dateFormatter.format(newDate.getTime()));
					}

				},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
				datePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
				datePickerDialog.show();
			}
		});

		continueBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TextUtils.isEmpty(edNama.getText().toString()) ||TextUtils.isEmpty(edEmail.getText().toString()) || TextUtils.isEmpty(edPhone.getText().toString()) || TextUtils.isEmpty(edPassword.getText().toString()) ) {
					Toast.makeText(SignUp.this, "Empty field not allowed!", Toast.LENGTH_SHORT).show();
				} else {
					Intent pin = new Intent(SignUp.this, SignUpPin.class);
					startActivity(pin);
				}
			}
		});





	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
		String item = adapterView.getItemAtPosition(i).toString();
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {

	}
}
